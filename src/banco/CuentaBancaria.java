package banco;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class CuentaBancaria {

    private final String numeroCuenta;
    private double saldo;
    protected static final Logger LOGGER = Logger.getLogger(CuentaBancaria.class.getName());


    public CuentaBancaria(String numeroCuenta, double saldoInicial) {
        this.numeroCuenta = numeroCuenta;
        this.saldo = saldoInicial;

    private CuentaBancaria(Builder builder) {
        this.numeroCuenta = builder.numeroCuenta;
        this.saldo = builder.saldo;
        this.historialRetiros = new ArrayList<>();
    }


    public static class Builder {
        private final String numeroCuenta;
        private double saldo;

        public Builder(String numeroCuenta) {
            this.numeroCuenta = numeroCuenta;
        }

        public Builder saldoInicial(double saldoInicial) {
            this.saldo = saldoInicial;
            return this;
        }

        public CuentaBancaria build() {
            return new CuentaBancaria(this);
        }
    }

    public void depositar(double monto) {
        if (monto > 0) {
            saldo += monto;
            LOGGER.info(String.format("Deposito exitoso. Saldo actual: $%.2f", saldo));
        } else {
            LOGGER.info("El monto a depositar debe ser positivo.");
        }
    }

    public void retirar(double monto){
        if(monto > saldo){
            LOGGER.info("El monto a retirar es mayor que su saldo");
        } else if (monto < 0) {
            LOGGER.info("No se permiten retirar valores negativos");
        } else {
            saldo -= monto;
            LOGGER.info(String.format("Se ha retirado satisfactoriamente de su cuenta $%.2f, su saldo actual es de $%.2f", monto, saldo));
        }
    }

    public double obtenerSaldo() {
        return saldo;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

}