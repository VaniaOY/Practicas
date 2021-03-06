
package edu.ipn.cecyt9.calculadora;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

/**
 * 
 * Interfaz para nuestra calculadora basica
 * 
 * @author:  emmanuel 
 * @version:  1.0 
 * @date: 06-09-2015 
 */
public class Calculadora extends JFrame {

	/**
	 * generado
	 */
	private static final long serialVersionUID = 1583724102189855698L;

	/** numero tecleado */
	JTextField pantalla,pantalla2;

	/** guarda el resultado de la operacion anterior o el número tecleado */
	double resultado;

        double resultado2;
	/** para guardar la operacion a realizar */
	String operacion;

	/** Los paneles donde colocaremos los botones */
	JPanel panelNumeros, panelOperaciones;

	/** Indica si estamos iniciando o no una operación */
	boolean nuevaOperacion = true;

	/**
	 * Constructor. Crea los botones y componentes de la calculadora
	 */
	public Calculadora() {
		super();
		setSize(450, 300);
		setTitle("Calculadora No Tan Simple");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setResizable(false);

		// Vamos a dibujar sobre el panel
		JPanel panel = (JPanel) this.getContentPane();
		panel.setLayout(new BorderLayout());

		pantalla = new JTextField("0", 20);
		pantalla.setBorder(new EmptyBorder(24, 24, 4, 20));
		pantalla.setFont(new Font("Arial", Font.BOLD, 25));
		pantalla.setHorizontalAlignment(JTextField.RIGHT);
		pantalla.setEditable(false);
		pantalla.setBackground(Color.WHITE);
		panel.add("North", pantalla);
                
                pantalla2 = new JTextField("0", 10);
		pantalla2.setBorder(new EmptyBorder(2, 2, 2, 20));
		pantalla2.setFont(new Font("Arial", Font.BOLD, 15));
		pantalla2.setHorizontalAlignment(JTextField.RIGHT);
		pantalla2.setEditable(false);
		pantalla2.setBackground(Color.WHITE);
		panel.add("South", pantalla2);

		panelNumeros = new JPanel();
		panelNumeros.setLayout(new GridLayout(4, 3));
		panelNumeros.setBorder(new EmptyBorder(4, 4, 4, 10));

		for (int n = 9; n >= 0; n--) {
			nuevoBotonNumerico("" + n);
		}

		nuevoBotonNumerico(".");

		panel.add("Center", panelNumeros);

		panelOperaciones = new JPanel();
		panelOperaciones.setLayout(new GridLayout(6, 1));
		panelOperaciones.setBorder(new EmptyBorder(4, 4, 4, 40));

		nuevoBotonOperacion("CE");
		nuevoBotonOperacion("+");
                nuevoBotonOperacion("^");
		nuevoBotonOperacion("-");
		nuevoBotonOperacion("!");
		nuevoBotonOperacion("*");
		nuevoBotonOperacion("√");
		nuevoBotonOperacion("/");
		nuevoBotonOperacion("bin");
		nuevoBotonOperacion("sen");
		nuevoBotonOperacion("=");

		panel.add("East", panelOperaciones);

		validate();
	}

	/**
	 * Crea un boton del teclado numérico y enlaza sus eventos con el listener
	 * correspondiente
	 * 
	 * @param digito
	 *            boton a crear
	 */
        	private void nuevoBotonNumerico(String digito) {
		JButton btn = new JButton();
		btn.setText(digito);        
		btn.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent evt) {
				JButton btn = (JButton) evt.getSource();
				numeroPulsado(btn.getText());
			}
		});

		panelNumeros.add(btn);
	}

	/**
	 * Crea un botón de operacion y lo enlaza con sus eventos.
	 * 
	 * @param operacion
	 */
	private void nuevoBotonOperacion(String operacion) {
		JButton btn = new JButton(operacion);
		btn.setForeground(Color.RED);

		btn.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent evt) {
				JButton btn = (JButton) evt.getSource();
				operacionPulsado(btn.getText());
			}
		});

		panelOperaciones.add(btn);
	}

	/**
	 * Gestiona las pulsaciones de teclas numéricas
	 * 
	 * @param digito
	 *            tecla pulsada
	 */
        boolean puntos = false;
	private void numeroPulsado(String digito) {
            	if (pantalla.getText().equals("0") || nuevaOperacion) {
			pantalla.setText(digito);
			pantalla2.setText(pantalla2.getText()+digito);
		} else {
			pantalla.setText(pantalla.getText() + digito);
			pantalla2.setText(pantalla2.getText() + digito);
		}
		nuevaOperacion = false;
        }

	/**
	 * Gestiona el gestiona las pulsaciones de teclas de operación
	 * 
	 * @param tecla
	 */
	private void operacionPulsado(String tecla) {
		if (tecla.equals("=")) {
			calcularResultado();
		} else if (tecla.equals("CE")) {
			resultado = 0;
			pantalla.setText("");
			pantalla2.setText("");
			nuevaOperacion = true;
		} else {
			operacion = tecla;
			if ((resultado > 0) && !nuevaOperacion) {
				calcularResultado();
			} else {
                            resultado = new Double(pantalla.getText());
                                pantalla2.setText(pantalla.getText()+ tecla);
                                
			}
		}

		nuevaOperacion = true;
	}

	/**
	 * Calcula el resultado y lo muestra por pantalla
	 */
	private void calcularResultado() {
		if (operacion.equals("+")) {
			resultado += new Double(pantalla.getText());
		} else if (operacion.equals("-")) {
			resultado -= new Double(pantalla.getText());
		} else if (operacion.equals("/")) {
			resultado /= new Double(pantalla.getText());
		} else if (operacion.equals("*")) {
			resultado *= new Double(pantalla.getText());
		} else if (operacion.equals("^")) {
			resultado = Math.pow(resultado, new Double(pantalla.getText()));
		} else if (operacion.equals("!")) {
                        for (double i = (resultado-1); i >0; i--) {
                            resultado *= i;
                        }
		} else if (operacion.equals("√")) {
			resultado = Math.sqrt(resultado);
		}else if (operacion.equals("bin")) {
			int res = (int) resultado;
                        resultado = Integer.valueOf(Integer.toBinaryString(res)); 
		} else if (operacion.equals("sen")) {
                        resultado = Math.sin(Math.toRadians(resultado)); 
		}

		pantalla.setText("" + resultado);
		pantalla2.setText(pantalla2.getText()+"=" + resultado);
		operacion = "";
	}
	
}
