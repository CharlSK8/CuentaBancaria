package banco;
public class Banco {
    public static void main(String[] args) {
        String numeroCuenta = "123456789";

        CuentaBancaria cuenta = CuentaBancariaFactory.crearCuenta("bancaria", numeroCuenta, 1000.0);
        cuenta.depositar(500.0);
        cuenta.retirar(250.0);
        cuenta.mostrarHistorialRetiros();

        CuentaBancariaAhorros cuentaAhorros = (CuentaBancariaAhorros) CuentaBancariaFactory.crearCuenta("ahorros", numeroCuenta, 1000.0, 5.0);
        cuentaAhorros.aplicarInteres();

        CuentaBancariaCorriente cuentaCorriente = (CuentaBancariaCorriente) CuentaBancariaFactory.crearCuenta("corriente", numeroCuenta, 100.0, 50);
        cuentaCorriente.retirar(130);
    }
}