package banco;
public class CuentaBancariaCorriente extends CuentaBancaria{
    private double limiteSobregiro;

    public CuentaBancariaCorriente(String numeroCuenta, double saldoInicial, double limiteSobregiro) {
        super(numeroCuenta, saldoInicial);
        this.limiteSobregiro = limiteSobregiro;
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
