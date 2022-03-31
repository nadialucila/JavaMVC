package utiles;

public interface Validadores {
	
	static final String ERROR = "Por favor, ingrese solo números en este campo.";
	static final String ERROR2 = "El número debe ser mayor a 0";
	
	static boolean isInteger(String string) {
		try {
			Integer.parseInt(string);
			return true;
		} catch(NumberFormatException e) {
			return false;
		}
	}
	
	static boolean isDouble(String string) {
		try {
			Double.parseDouble(string);
			return true;
		} catch(NumberFormatException e) {
			return false;
		}
	}
	
	static boolean isPositive(Double numero) {
		if (numero > 0) {
			return true;
		} else {
			return false;
		}
	}
	// request.setAttribute("errorInput", Validadores.ERROR);
	//  <i><c:out value="${errorInput}"/></i>
}
