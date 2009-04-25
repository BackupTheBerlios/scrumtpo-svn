#!/usr/bin/python
# -*- coding: utf-8 -*-

# This script takes mysql script as a first argument and converts all tables into
# Java enum representations

import sys

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

if len(sys.argv) != 2:
	print "Usage: python extract.py mysql.sql"
else:
	try:
		f = open(sys.argv[1])

		TABLE, NOTABLE = range(2)

		currentMembers = []

		state = NOTABLE
		for line in f:
			if state == NOTABLE:
				if line.find("create table") != -1:
					splitted = line.strip().split()
					print "public enum", substUnder(splitted[2]) + "Enum {", 
					state = TABLE
					currentMembers = []					
			else:				
				if line[0] == ")":
					state = NOTABLE
					print ", ".join(currentMembers), "}"
				elif (line[0] == "(") or (line.find("constraint") != -1) or (line.find("ENUM") != -1):
					pass
				else:
					splitted = line.strip().split()
					currentMembers.append(substUnder(splitted[0]))

		f.close()
	except Exception, msg:
		print "Error while parsing file:", sys.argv[1], "-", msg
