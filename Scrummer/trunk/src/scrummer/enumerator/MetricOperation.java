package scrummer.enumerator;

/**
 * Metric related operations
 */
public enum MetricOperation {
	/// Measure table related operation
	Measure,
	/// task related measure dop
	TaskMeasure,
	/// release related measure dop
	ReleaseMeasure,
	/// sprint related measure dop
	SprintMeasure,
	/// pbi related measure dop
	PBIMeasure,
	/// work effectiveness was calculated
	WorkEffectivenessCalculated,
	/// earned value indicator was calculated
	EarnedValueCalculated,
	/// spi was calculated
	SPICalculated,
	/// cpi was calculated
	CPICalculated
}
