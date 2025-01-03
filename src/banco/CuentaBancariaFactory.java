package banco;

public class CuentaBancariaFactory {

    private CuentaBancariaFactory() {
    }

    public static CuentaBancaria crearCuenta(String tipo, String numeroCuenta, double saldoInicial, double ...parametros) {
        switch (tipo.toLowerCase()) {
            case "ahorros":
                return new CuentaBancariaAhorros.Builder(numeroCuenta).saldoInicial(saldoInicial).tasaInteres(parametros[0]).build();
            case "corriente":
                return new CuentaBancariaCorriente.Builder(numeroCuenta).saldoInicial(saldoInicial).limiteSobregiro(parametros[0]).build();
            default:
                return new CuentaBancaria.Builder(numeroCuenta).saldoInicial(saldoInicial).build();
        }
    }
}
