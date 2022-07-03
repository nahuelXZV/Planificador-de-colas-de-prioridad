package planificadorcolasprioridad;

/**
 *
 * @author Nahuel
 */
public class PCB {

    String PID;
    String ImagenName;
    int registers[];
    int prioridad;

    public PCB(String PID, int prioridad) {
        this.PID = PID;
        this.prioridad = prioridad;
    }

    public String getPID() {
        return PID;
    }

    public void setPID(String PID) {
        this.PID = PID;
    }

    public String getImagenName() {
        return ImagenName;
    }

    public void setImagenName(String ImagenName) {
        this.ImagenName = ImagenName;
    }

    public int[] getRegisters() {
        return registers;
    }

    public void setRegisters(int[] registers) {
        this.registers = registers;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }

}
