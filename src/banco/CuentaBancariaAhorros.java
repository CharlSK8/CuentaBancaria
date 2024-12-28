package banco;

public class CuentaBancariaAhorros extends CuentaBancaria{
    private double tasaInteres;

    public CuentaBancariaAhorros(String numeroCuenta, double saldoInicial, double tasaInteres) {
        super(numeroCuenta, saldoInicial);
        this.tasaInteres = tasaInteres;
    }

    public void aplicarInteres() {
        if(tasaInteres > 0){
            double interes = obtenerSaldo() * tasaInteres / 100;
            depositar(interes);
            LOGGER.info("Interes aplicado: " + interes);
        }else {
            LOGGER.info("La tasa de interés debe ser mayor a 0");
        }

    }
    public double getTasaInteres() {
        return tasaInteres;
    }

    public void setTasaInteres(double tasaInteres) {
        this.tasaInteres = tasaInteres;
    }

}
