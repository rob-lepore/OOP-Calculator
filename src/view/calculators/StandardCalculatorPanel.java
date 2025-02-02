package view.calculators;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JPanel;
import view.components.CCDisplay;
import view.components.CCNumPad;
import controller.calculators.CalculatorController;
import controller.calculators.logics.InputFormatterLogics;
import controller.calculators.logics.InputFormatterLogicsImpl;
import controller.calculators.logics.OutputFormatterLogics;
import controller.calculators.logics.OutputFormatterLogicsImpl;
import model.manager.EngineModelInterface.Calculator;
import utils.CreateButton;
/**
 * This is StandardCalculatorPanel which holds the basic operators:
 * -plus.
 * -subtraction.
 * -multiplication.
 * -division.
 * -square.
 * -inverse.
 * -modulo.
 */
public class StandardCalculatorPanel extends JPanel {
    /**
     * 
     */
    private static final long serialVersionUID = -3801351406960094788L;
    private final CCDisplay display = new CCDisplay();
    private final transient CalculatorController controller = Calculator.STANDARD.getController();
    private final transient InputFormatterLogics inFormatter;
    private final transient OutputFormatterLogics outFormatter;
    /**
      * This is StandardCalculatorPanel which holds the basic operators:
      * -plus.
      * -subtraction.
      * -multiplication.
      * -division.
      * -square.
      * -inverse.
      * -modulo.
     */
    public StandardCalculatorPanel() {
        this.inFormatter = new InputFormatterLogicsImpl(this.controller);
        this.outFormatter = new OutputFormatterLogicsImpl(this.controller, this.display);
        this.setLayout(new BorderLayout());
        this.add(display, BorderLayout.NORTH);
        this.setNumbers();
        this.setOperators();
    }
    private void setNumbers() {
        final JPanel numbers = new CCNumPad(e -> {
            inFormatter.read(((JButton) e.getSource()).getText());
            outFormatter.updateDisplay();
        }, e -> {
            if (!controller.getManager().memory().getCurrentState().isEmpty()) {
                final String history = outFormatter.format();
                outFormatter.updateDisplayUpperText();
                inFormatter.calculate();
                outFormatter.addResult(history);
            }
            outFormatter.updateDisplay();
        }, e -> {
            inFormatter.deleteLast();
            outFormatter.updateDisplay();
        });
        this.add(numbers, BorderLayout.CENTER);
    }
    private void setOperators() {
        final JPanel operator = new JPanel();
        operator.setLayout(new GridLayout(4, 2));
        final var standardOp = List.of("+", "-", "×", "÷", "%", "1/x", "√", "x²");
        standardOp.forEach((op) -> {
            final JButton btn = CreateButton.createOpButton(op);
            btn.addActionListener(e -> {
               this.inFormatter.read(op);
               this.outFormatter.updateDisplay();
            });
            operator.add(btn);
        });

        this.add(operator, BorderLayout.EAST);
    }
}
