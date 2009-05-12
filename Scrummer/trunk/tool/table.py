#!/usr/bin/python
# -*- coding: utf-8 -*-

import sys

## Table representation class
class Table(object):

	## Constructor
	def __init__(self):
		# table name
		self.__name = ""
		# primary key fields
		self.__primary = []
		# fields
		self.__fields = []	

	## add field to table schema representation
	def addField(self, value):
		self.__fields.append(value)

	## find all fields with given name and make them primary kes
	def setPrimaryKey(self, name):
		fields = []
		for field in self.__fields:
			if field.Name == name:
				self.__primary.append(field)				
				field.PrimaryKey = True
				fields.append(field)
		for f in fields:
			self.__fields.remove(f)
	
	## generate insert statement
	def generateInsert(self):
		lst = [f.toJavaString() for f in self.__primary]
		lst2 = [f.toJavaString() for f in self.__fields]	
		if len(lst2) > 0:
			lst.extend(lst2)
		print "public boolean insert(" + ", ".join(lst) + ") {"

		print "\tboolean ret = false;"
		print "\tjava.sql.Connection conn      = null;"
		print "\tjava.sql.PreparedStatement st = null;"
		print "\tResultSet res = null;"
		print "\ttry {"
		print "\t\tconn = _connectionModel.getConnection();"
		print "\t\tString query ="
		print "\t\t\"INSERT INTO \" + " + "DBSchemaModel." + substUnder(self.Name) + "Table +"
		print "\t\t\"(\" +" 
		
		inserts = ["\t\t" + f.toDbName() for f in self.__fields]
		print " + \",\" + \n".join(inserts) + " + "
		print "\t\t\")\" + "

		print "\t\t\" WHERE \" + " 

		keys = ["\t\t" + f.toDbName() + " + \"=\" + " for f in self.__primary]
		nkeys = []
		i = 0
		for k in keys:
			nkeys.append(keys[i] + self.__primary[i].toMethodFieldName())
			i=i+1
		print " + \",\" + \n".join(nkeys) + " + "
		print "\t\t\")\";"

		print "\t\tst = conn.prepareStatement(query);"

		i = 1
		for f in self.__fields:
			print "\t\tst." + f.toRSSetter(i, self.__fields[i-1].toMethodFieldName()) + ";"
			i = i + 1
		
		print "\t\tst.execute();"
		print "\t\t_operation.operationSucceeded(DataOperation.Insert, , \"\");"
		print "\t\tret = true;"

		print "\t} catch (SQLException e) {"
		print "\t" + "\t" + "_operation.operationFailed(DataOperation.Insert, , e.getMessage());"
		print "\t\te.printStackTrace();"
		print "\t} finally {"
		print "\t\tres  = _connectionModel.close(res);"
		print "\t\tst   = _connectionModel.close(st);"
		print "\t\tconn = _connectionModel.close(conn);"
		print "\t}"
		print "\treturn ret;"

		print "}"

	## Generate update statements
	def generateUpdate(self):
		self.generateInsert()
		self.generateUpdateRow()
		self.generateRowGetter()
		self.generateRowUpdater()
		self.generateUpdateGet()
		self.generateUpdateSet()

	def generateUpdateRow(self):
		print "public static class Row extends DataRow {"
		print "\t/**"
		print "\t * Constructor"
		print "\t * "
		print "\t * @param result result from which to get data"
		print "\t */"
		print "\tpublic Row(ResultSet result) {"
		print "\t\ttry {"
		print "\t\t\tresult.beforeFirst(); result.next();"

		lst = []
		i = 1
		for f in self.__primary:
			add = "\t\t\t" + f.toJavaString() + " =\n\t\t\t\t" + f.toRSGetter(i)
			lst.append(add)
			i = i + 1
		for f in self.__fields:
			add = "\t\t\t" + f.toJavaString() + " =\n\t\t\t\t" + f.toRSGetter(i)
			lst.append(add)
			i = i + 1

		print "\n".join(lst)

		print "\t\t} catch (SQLException e) {"
		print "\t\t\te.printStackTrace();"
		print "\t\t}"
		print "\t}"
		print ""
		print "\t/**"
		print "\t * Does key equal" 
		print "\t * @param taskId"
		print "\t * @return true if row key equals this row"
		print "\t */"
		args = [f.toMethodFieldName() for f in self.__primary]
		print "\tpublic boolean keyEquals(" + ", ".join(args) + ") {"
		
		if len(args) == 0:
			print "\t\treturn true;"
		elif len(args) == 1:
			print "\t\tif (" + self.__primary[0].toMethodFieldName() + " == " + self.__primary[0].toJavaString() + ") {"
			print "\t\t\treturn true;"
			print "\t\t} else {"
			print "\t\t\treturn false;"
			print "\t\t}"
		else:
			lst = []
			i = 0
			for f in self.__primary:
				lst.append("(" + f.toMethodFieldName() + " == " + f.toJavaString() + ")")
			print "\t\tif (" + " &&\n\t\t    ".join(lst) + ") {"
			print "\t\t\treturn true;"
			print "\t\t} else {"
			print "\t\t\treturn false;"
			print "\t\t}"
		print "\t}"
		print "}"

	## generate row getter
	def generateRowGetter(self):
		args = [f.toJavaString() for f in self.__primary]
		print "public Row getRow(" + ", ".join(args) + ") {" 
		print "{"
		print "\tResultQuery<Row> q = new ResultQuery<Row>(_connectionModel) {"
		print "\t@Override"
		print "\tpublic void processResult(ResultSet result) {"
		print "\t\tsetResult(new Row(result));"
		print "\t}"
		print "\t@Override"
		print "\tpublic void handleException(SQLException ex) {"
		print "\t\tsetResult(null);"
		print "\t\tex.printStackTrace();"
		print "\t\t_operation.operationFailed(DataOperation.Remove, , "
		print "\t\ti18n.tr(\"\"));"
		print "\t}"
		print "\t};"
		print "\tq.queryResult("
		print "\t\"SELECT * FROM \" + DBSchemaModel." + substUnder(self.Name) + " + \" WHERE \" + "
		keys = ["\t" + f.toDbName() + " + \"=\" + " for f in self.__primary]
		nkeys = []
		i = 0
		for k in keys:
			nkeys.append(keys[i] + self.__primary[i].toMethodFieldName())
			i=i+1
		print " + \",\" + \n".join(nkeys) + ");"

		print "\treturn q.getResult();"
		print "}"
	
	## this thing generates a general row update function(updates some column with certain value)
	def generateRowUpdater(self):
	
		# args = [f.toMethodFieldName() for f in self.__primary]
		args = [f.toJavaString() for f in self.__primary]
		print "public boolean updateCell(" + ", ".join(args) + ", String column, String value) {"
		print "\tResultQuery<Boolean> q = new ResultQuery<Boolean>(_connectionModel) {"
		print "\t@Override"
		print "\tpublic void process() {"
		print "\t\tsetResult(true);"
		print "\t\t_operation.operationSucceeded("
		print "\t\tDataOperation.Update, , \"\");"
		print "\t}"
		print "\t@Override"
		print "\tpublic void handleException(SQLException ex) {"
		print "\t\tsetResult(false);"
		print "\t\t_operation.operationFailed(DataOperation.Update, , "
		print "\t\ti18n.tr(\"\"));"
		print "\t\tex.printStackTrace();"
		print "\t}"
		print "\t};"
		print "\tq.query(\"UPDATE \" + DBSchemaModel." + self.Name + "Table + "
		print "\t\" SET \" + column + \"='\" + value + \"'\" + "
		print "\t\" WHERE \" + "

		pklst = []
		i = 0
		for f in self.__primary:
			pklst.append("\t" + f.toDbName() + " + \"=\" + " + self.__primary[i].toMethodFieldName())
			i = i + 1			
		
		print " + \" AND \" + \n".join(pklst) + ");";

		print "\treturn q.getResult();"
		print "\t};"

	def generateUpdateGet(self):
		for f in self.__fields:
			# generate common header(primary key)
			args = [f.toJavaString() for f in self.__primary]
			fieldNames = [f.toMethodFieldName() for f in self.__primary]
			print "public boolean get" + substUnder(f.Name) + "(" + ", ".join(args) + ") {"
			print "\treturn getRow(" + ", ".join(fieldNames) + ")." + substUnder(f.Name) + ";"
			print "}"

	def generateUpdateSet(self):

		for f in self.__fields:
			# generate common header(primary key)
			args = [g.toJavaString() for g in self.__primary]
			args.append("String value")
			fieldNames = [g.toMethodFieldName() for g in self.__primary]
			fieldNames.append("DBSchemaModel." + substUnder(f.Name))
			fieldNames.append("value")

			print "public boolean set" + substUnder(f.Name) + "(" + ", ".join(args) + ") {"
			print "\treturn updateCell(" + ", ".join(fieldNames) + ");"
			print "}"

	def generateDelete(self):
		lst = [f.toJavaString() for f in self.__primary]
		print "public boolean remove(" + ", ".join(lst) + ") {"
		print "\tResultQuery<Boolean> q = new ResultQuery<Boolean>(_connectionModel) {"
		print "\t@Override"
		print "\tpublic void process() {"
		print "\t\tsetResult(true);"
		print "\t\t_operation.operationSucceeded(DataOperation.Remove, , "");"
		print "\t}"
		print "\t@Override"
		print "\tpublic void handleException(SQLException ex) {"
		print "\t\tsetResult(false);"
		print "\t\tex.printStackTrace();"
		print "\t\t_operation.operationFailed(DataOperation.Remove, , "
		print "\t\ti18n.tr(""));"
		print "\t}"
		print "\t};"
		print "\tq.query(\"DELETE FROM \" + DBSchemaModel." + substUnder(self.Name) + " + "
		print "\t\" WHERE \" + " 

		keys = ["\t" + f.toDbName() + " + \"=\" + " for f in self.__primary]
		nkeys = []
		i = 0
		for k in keys:
			nkeys.append(keys[i] + self.__primary[i].toMethodFieldName())
			i=i+1
		print " + \",\" + \n".join(nkeys)
		print "\t);"

		print "\treturn q.getResult();"
		print "}"

	## add primary key to table schema representation
	def addPrimary(self, value):
		self.__primary = value

	def __str__(self):
		ret = self.__name + "\n"
		for field in self.__primary:
			ret = ret + str(field) + "\n"
		for field in self.__fields:
			ret = ret + str(field) + "\n"
		return ret

	def getName(self):
		return self.__name

	def setName(self, value):
		self.__name = value

	## Table name
	Name = property(getName, setName)
	
## Table field contains type and name
class Field(object):

	## Construct field from table line
	def __init__(self, string):
		splitted = string.strip().split()
		self.__type = splitted[1].replace(",", "")
		self.__name = splitted[0]
		self.__isPk = False
				
	def getName(self):
		return self.__name

	def getType(self):
		return self.__type

	def isPrimaryKey(self):
		return self.__isPk
	
	def setPrimaryKey(self, value):
		self.__isPk = value
		
	def toMethodFieldName(self):
		name = substUnder(self.__name)
		return name[0].lower() + name[1:len(name)]

	## Convert field to java description
	def toJavaString(self):	
		return self.sqlToJavaType(self.__type) + " " + self.toMethodFieldName()

	def toDbName(self):
		return "DBSchemaModel." + substUnder(self.__name)

	def sqlToJavaType(self, sqlType):
		sqlType = sqlType.lower()
		if sqlType == "integer":
			return "int"
		elif sqlType == "tinyint":
			return "int"
		elif sqlType == "smallint":
			return "int"
		elif sqlType == "mediumint":
			return "int"
		elif sqlType == "int":
			return "int"
		elif sqlType == "bigint":
			return "int"
		elif sqlType == "long":
			return "long"
		elif sqlType == "boolean":
			return "boolean"
		elif sqlType == "numeric":
			return "BigInteger"
		elif sqlType == "decimal":
			return "BigInteger"
		elif sqlType == "float":
			return "float"
		elif sqlType == "real":
			return "float"
		elif sqlType == "date":
			return "java.sql.Date"
		elif sqlType == "char" or (sqlType.find("char") != -1):
			return "String"
		elif sqlType == "varchar" or (sqlType.find("varchar") != -1):
			return "String"
		elif sqlType == "text" or (sqlType.find("text") != -1):
			return "String"
		else:
			raise Exception, "Unknown sql type: " + sqlType

	## Generate getter for this type
	# @return string that can be use to get something from resultset
	def toRSGetter(self, sequence):
		sequence = str(sequence)
		sqlType = self.__type.lower()
		if sqlType in ("integer", "tinyint", "smallint", "mediumint", "int", "bigint"):
			return "getInt(" + sequence + ")"
		elif sqlType == "long":
			return "getLong(" + sequence + ")"
		elif sqlType == "boolean":
			return "getBoolean(" + sequence + ")"
		elif sqlType == "numeric":
			return "getBigDecimnal(" + sequence + ")"
		elif sqlType == "decimal":
			return "getBigDecimnal(" + sequence + ")"
		elif sqlType == "float":
			return "getFloat(" + sequence + ")"
		elif sqlType == "real":
			return "getFloat(" + sequence + ")"
		elif sqlType == "date":
			return "getDate(" + sequence + ")"
		elif sqlType == "char" or (sqlType.find("char") != -1):
			return "getString(" + sequence + ")"
		elif sqlType == "varchar" or (sqlType.find("varchar") != -1):
			return "getString(" + sequence + ")"
		elif sqlType == "text" or (sqlType.find("text") != -1):
			return "getString(" + sequence + ")"
		else:
			raise Exception, "Unknown sql type: " + sqlType

	## Generate setter for this type
	# @return string that can be use to set something in resultset
	def toRSSetter(self, sequence, value):
		sequence = str(sequence)
		sqlType = self.__type.lower()
		if sqlType in ("integer", "tinyint", "smallint", "mediumint", "int", "bigint"):
			return "setInt(" + sequence + ", " + value + ")"
		elif sqlType == "long":
			return "setLong(" + sequence + ", " + value + ")"
		elif sqlType == "boolean":
			return "setBoolean(" + sequence + ", " + value + ")"
		elif sqlType == "numeric":
			return "setBigDecimnal(" + sequence + ", " + value + ")"
		elif sqlType == "decimal":
			return "setBigDecimnal(" + sequence + ", " + value + ")"
		elif sqlType == "float":
			return "setFloat(" + sequence + ", " + value + ")"
		elif sqlType == "real":
			return "setFloat(" + sequence + ", " + value + ")"
		elif sqlType == "date":
			return "setDate(" + sequence + ", " + value + ")"
		elif sqlType == "char" or (sqlType.find("char") != -1):
			return "setString(" + sequence + ", " + value + ")"
		elif sqlType == "varchar" or (sqlType.find("varchar") != -1):
			return "setString(" + sequence + ", " + value + ")"
		elif sqlType == "text" or (sqlType.find("text") != -1):
			return "setString(" + sequence + ", " + value + ")"
		else:
			raise Exception, "Unknown sql type: " + sqlType

	def __str__(self):
		pk = ""
		if self.__isPk:
			pk = " PRIMARY KEY"
		return self.__type + " " + self.__name + pk

	## Field name
	Name = property(getName)
	## Field type
	Type = property(getType)
	## Is this field a primary key
	PrimaryKey = property(isPrimaryKey, setPrimaryKey)

## Substitute all _char combinations in string with Char
def substUnder(string):
	i = string.find("_")
	while i != -1:
		# string[i] = string[i].capitalize()
		if i == len(string) - 1:
			string = string[0:i]	
		else:
			string = string[0:i] + string[i+1].capitalize() + string[i+2:len(string)]
		i = string.find("_")
	return string

if len(sys.argv) != 2 and len(sys.argv) != 3:
	print "Usage: python table.py mysql.sql tableName"
else:
	try:
		f = open(sys.argv[1])
		if len(sys.argv) > 2:
			table = sys.argv[2]
		else:
			table = None

		TABLE, NOTABLE = range(2)

		currentTable = Table()
		currentMembers = []

		state = NOTABLE
		for line in f:
			if state == NOTABLE:
				if line.find("create table") != -1:
					splitted = line.strip().split()
					state = TABLE
					currentMembers = []
					currentTable = Table()
					currentTable.Name = substUnder(splitted[2])
			else:				
				if line[0] == ")":
					state = NOTABLE
					if table != None:
						print table, currentTable.Name
						if table == currentTable.Name:
							currentTable.generateUpdate()
					else:
						currentTable.generateUpdate()
				elif (line.find("constraint") != -1):
					# get everything inbetween parentheses
					i1 = line.find("(")
					i2 = line.find(")")
					if (i1 != -1):
						splitted1 = line[i1+1:i2].split(",")
						splitted = []
						for i in splitted1:
							splitted.append(i.strip())
						for fieldName in splitted:
							currentTable.setPrimaryKey(fieldName)
				elif (line[0] == "(") or (line.find("ENUM") != -1):
					pass
				else:
					fi = Field(line)
					currentTable.addField(fi)

		f.close()
	except Exception, msg:
		print "Error while parsing file:", sys.argv[1], "-", msg
