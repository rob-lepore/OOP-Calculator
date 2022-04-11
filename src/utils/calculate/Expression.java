package utils.calculate;

import java.util.List;
import java.util.Optional;

import controller.manager.CCEngine;
import utils.CalcException;
import utils.ast.Operation;
import utils.tokens.Token;

/**
 * @author pesic
 *
 */
public class Expression {
	
	private String expr;
	private EvaluatorAST evaluator = new EvaluatorAST();
	private ParserAST parser = new ParserAST();
	private Optional<Operation> result = Optional.empty();
	
	public void setExpr(String expr) {
		this.expr = expr;
		result = Optional.empty();
	}
	
	public void setEngine( final CCEngine engine) {
	    this.parser.setEngine(engine);
	}
	
	public Operation getResult() throws CalcException {
		this.result = Optional.of(evaluator.evaluate(parser.parseToAST(this.expr)));
		return result.get();
	}
	
	public Operation getDerivative() throws CalcException {
		if(result.isEmpty()) {
			getResult();
		}
		this.result = Optional.of(result.get().getDerivative());
		return result.get();
	}
	
	public String toString() {
		return this.result.toString();
	}
	
	
}
