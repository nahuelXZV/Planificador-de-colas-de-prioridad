package planificadorcolasprioridad;

import java.util.LinkedList;

/**
 *
 * @author Nahuel
 */
public class Planificador {

    LinkedList<LinkedList<PCB>> Q = new LinkedList<>();     //Lista de listas de PCB
    public String[] Letra = {"", "A", "B", "C", "D", "F", "G", "H", "I", "J"};          //Letra para las colas y procesos

    public PCB prun;           //PCB en CPU
    public int n = 3;          //Numero de colas
    public int[] Lqxp;         //Lista de los qxp en orden
    public int[] Lqxc;         //Lista de los qxc en orden
    int k = 1;              //next
    int qxp = 0;            //qxp acumulado
    int qxc = 0;            //qxc acumulado

    //POR DEFECTO
    public Planificador() {
        iniciarColas();                     //INICIA LAS COLAS
        initQuantums();                     //INICIAMOS LOS QUANTUMS
        initColas();                        //LLENAMOS LAS COLAS CON PROCESOS
        prunIniciador();                    //SACA EL PRIMER PRUN
    }

    //PERSONALIZADO
    public Planificador(int n, int[] Lqxp, int[] Lqxc) {
        this.n = n;                         //CANTIDAD DE COLAS
        iniciarColas();                     //INICIA LAS COLAS
        initQuantums(Lqxp, Lqxc);           //INICIAMOS LOS QUAMTUMS
        initColas();                        //LLENAMOS LAS COLAS CON PROCESOS
        prunIniciador();                    //SACA EL PRIMER PRUN
    }

    //CREAS LAS INSTANCIAS DE LAS COLAS
    private void iniciarColas() {
        for (int i = 0; i <= n; i++) {
            Q.add(new LinkedList<>());      //INICIAMOS LAS COLAS
        }
    }

    //SACA EL PRIMER PRUN PARA MOSTRAR
    private void prunIniciador() {
        prun = Q.get(1).remove(1);      //PRUN DE INICIO
        qxc++;
        if (qxc == Lqxc[prun.prioridad]) {      //Verificamos los qxc
            k = next(k);                        //siguiente cola
            qxc = 0;
        }
    }

    //INICIALIZA LOS QUAMTUMS POR DEFECTO
    private void initQuantums() {
        Lqxp = new int[n + 1];
        Lqxp[1] = 1;
        Lqxp[2] = 1;
        Lqxp[3] = 1;
        Lqxc = new int[n + 1];
        Lqxc[1] = 1;
        Lqxc[2] = 1;
        Lqxc[3] = 1;
    }

    //CLONAMOS LAS LISTAS DE QUANTUMS
    private void initQuantums(int[] Lqxp, int[] Lqxc) {
        this.Lqxp = Lqxp.clone();
        this.Lqxc = Lqxc.clone();
    }

    //RELLENAMOS LAS COLAS CON PROCESOS
    private void initColas() {
        for (int i = 1; i <= n; i++) {
            int alet = (int) (Math.random() * 8 + 1);       //cantidad de procesos que tendra la cola MAX-8
            Q.get(i).addLast(new PCB("COLA " + Letra[i], i));       //COLA A - Nombre
            for (int j = 1; j <= alet; j++) {                   //PROCESOS
                Q.get(i).addLast(new PCB(Letra[i] + j, i));         //A1,A2 
            }
        }
    }

    //MOSTRAR PLANIFICADOR
    public void mostrarPlan() {
        for (int i = 1; i <= n; i++) {
            System.out.print(i + " = ");
            for (int j = 0; j <= Q.get(i).size() - 1; j++) {
                System.out.print(Q.get(i).get(j).PID + ", ");
            }
            System.out.println("");
        }
    }

    //OBTENER EL QXP DEL PRUN ACTUAL
    public int getQXP() {
        return Lqxp[prun.prioridad];
    }

    //OBTENER EL QXC DEL PRUN ACTUAL
    public int getQXC() {
        return Lqxc[prun.prioridad];
    }

    //OBTENER UNA COLA MEDIANTE LA PRIORIDAD
    public String[] getQ(int i) {
        String[] q = new String[Q.get(i).size()];
        for (int j = 0; j <= Q.get(i).size() - 1; j++) {
            if (i == prun.prioridad && j == 0) {
                q[j] = "→ " + Q.get(i).get(j).PID;
            } else {
                q[j] = Q.get(i).get(j).PID;
            }
        }
        return q;
    }

    //CODIGO PRINCIPAL DE PLANIFICADOR
    public PCB plan() {
        qxp++;
        if (qxp == Lqxp[prun.prioridad]) {          //Verificamos los qxp
            Q.get(prun.prioridad).addLast(prun);        //Volvemos a la cola

            prun = Q.get(k).remove(1);      //Sacamos el numero PRUN
            qxc++;
            if (qxc == Lqxc[prun.prioridad]) {      //Verificamos los qxc
                k = next(k);                        //siguiente cola
                qxc = 0;
            }
            qxp = 0;
        }
        return prun;
    }

    //DEVUELVE LA SIGUIENTE COLA
    private int next(int k) {
        if (k == n) {
            return 1;
        }
        return k + 1;
    }

}
