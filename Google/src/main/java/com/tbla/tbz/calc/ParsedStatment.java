package com.tbla.tbz.calc;

// TODO add a builder
public class ParsedStatment {
	private String variableName = null;
	private final Memory memory;
	public ParsedStatment(Memory memory) {
		this.memory = memory;
	}
	public String getVariableName() {
		return variableName;
	}
	public void setVariableName(String variableName) {
		this.variableName = variableName;
	}
	public CalcExpression getCalcExpression() {
		return new CalcExpression();
	}
	public void evaluateUnairyOprators() {
		// TODO Auto-generated method stub
		
	}
	public void dereferenceVariables() {
		// TODO Auto-generated method stub
		
	}
	public void evaluateUnaryOperators() {
		// TODO Auto-generated method stub
		
	}
	public Integer evaluate() {
		
		return 12;
	}
	public void updatePostfixOperatorVariables() {
		// TODO Auto-generated method stub
		
	}
}
