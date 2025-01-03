package banco;
public class CuentaBancariaCorriente extends CuentaBancaria{
    private double limiteSobregiro;

    private CuentaBancariaCorriente(Builder builder) {
        super(builder.numeroCuenta, builder.saldoInicial);
        this.limiteSobregiro = builder.limiteSobregiro;
    }

    public static class Builder {
        private final String numeroCuenta;
        private double saldoInicial;
        private double limiteSobregiro;

        public Builder(String numeroCuenta) {
            this.numeroCuenta = numeroCuenta;
        }

        public Builder saldoInicial(double saldoInicial) {
            this.saldoInicial = saldoInicial;
            return this;
        }

        public Builder limiteSobregiro(double limiteSobregiro) {
            this.limiteSobregiro = limiteSobregiro;
            return this;
        }

        public CuentaBancariaCorriente build() {
            return new CuentaBancariaCorriente(this);
        }
    }

    @Override
    public void retirar(double monto) {
        if (!esMontoValido(monto)) {
            LOGGER.info("El monto a retirar debe ser mayor que 0.");
            return;
        }

        if (puedeRetirarse(monto)) {
            procesarRetiro(monto);
            LOGGER.info("Retiro exitoso. Nuevo saldo: " + obtenerSaldo() + ", Limite de sobregiro restante: " + limiteSobregiro);
        } else {
            LOGGER.info("Fondos insuficientes o monto inválido, incluso con sobregiro.");
        }
    }

    private boolean esMontoValido(double monto) {
        return monto > 0;
    }

    private boolean puedeRetirarse(double monto) {
        double saldoTotalDisponible = super.obtenerSaldo() + limiteSobregiro;
        return monto <= saldoTotalDisponible;
    }

    private void procesarRetiro(double monto) {
        if (monto > super.obtenerSaldo()) {
            double sobregiroNecesario = monto - super.obtenerSaldo();
            super.retirar(super.obtenerSaldo());
            this.limiteSobregiro -= sobregiroNecesario; 
        } else {
            super.retirar(monto); 
        }
    }

    public double getLimiteSobregiro() {
        return limiteSobregiro;
    }

    public void setLimiteSobregiro(double limiteSobregiro) {
        this.limiteSobregiro = limiteSobregiro;
    }
}
