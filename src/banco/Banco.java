package banco;
public class Banco {
    public static void main(String[] args) {
        String numeroCuenta = "123456789";

        // Instancia de Cuenta Bancaria
        CuentaBancaria cuenta = new CuentaBancaria(numeroCuenta, 1000.0);
        cuenta.depositar(500.0);
        cuenta.retirar(250.0);

        // Instancia de Cuenta de Ahorros
        CuentaBancariaAhorros cuentaAhorros = new CuentaBancariaAhorros(numeroCuenta, 1000.0, 5);
        cuentaAhorros.aplicarInteres();

        // Instancia de Cuenta Corriente
        CuentaBancariaCorriente cuentaCorriente = new CuentaBancariaCorriente(numeroCuenta, 100, 50);
        cuentaCorriente.retirar(130);
    }
}
