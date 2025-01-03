package banco;

public class CuentaBancariaAhorros extends CuentaBancaria{
    private double tasaInteres;

    private CuentaBancariaAhorros(Builder builder) {
        super(builder.numeroCuenta, builder.saldoInicial);
        this.tasaInteres = builder.tasaInteres;
    }

    public static class Builder {
        private final String numeroCuenta;
        private double saldoInicial;
        private double tasaInteres;

        public Builder(String numeroCuenta) {
            this.numeroCuenta = numeroCuenta;
        }

        public Builder saldoInicial(double saldoInicial) {
            this.saldoInicial = saldoInicial;
            return this;
        }

        public Builder tasaInteres(double tasaInteres) {
            this.tasaInteres = tasaInteres;
            return this;
        }

        public CuentaBancariaAhorros build() {
            return new CuentaBancariaAhorros(this);
        }
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
