/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

import java.io.*;
import java.util.*;

/**
 * "PROYECTO FINAL LABERINTO"
 * Equipo: Powerpuff Girls and Daniel
 * Este programa nos ayuda a la resolución de un laberinto así como las rutas que tomo
 * Integrantes:
 * Estefania Grave Angulo 220217226
 * isabel Alejandra Reyes Zamora 220203728
 * Daniel Octavio Juarez Torres 220201710
 * Fecha: Jueves 2 de diciembre del 2021
 * "Redacción de lo realizado"
 * Lo primero es el menu final, el cual se cicla mientras no salga, luego se creo el “crear vértices”,
 * el cuan consiste en agarrar la matriz y la checa 1 por 1 y si tiene espacio, la guarda su coordenada en la matriz,
 * y ese vertice es por ejemplo x=0, y=1.Eso guardamos en el vertice.
 * Cuando ya tenemos los vértices creamos los arcos, lo cual se hace ciclando los vértices de la linkedlist y atodos los vértices
 * se les busca si tiene en las coordenadas de arriba, abajo, izquierda y derecha y si si, pues crea el arco del vertice.
 * Luego es lo mismo que con las tareas, nomas que las coordenadas x, y sustituyen el nombre del vertice.
 * se hace la búsqueda del camino, con dfs o bfs, para ambos se busca la entrada y salida,
 * la entrada lo hace ciclando el linkedlist de vértices, buscando uno que cumpla tener x o y en 0 o en el limite de matriz,
 * por que significa que esta en el borde y por ahí se puede entrar.
 * Luego se busca la salida exactamente igual, solo agregando la condición de que no sea igual a la entrada.
 * Al momento de imprimir el laberinto resuelto,se cicla la matriz y al momento de ciclarla se busca si los espacios en blanco son vértices por las cuales paso,
 * y si son se les pone un # para marcar el camino.
 *
 */
class Registro {
    String origen;
    String destino;
    int peso;

    public Registro(String origen, String destino, String peso) {
        this.origen = origen;
        this.destino = destino;
        this.peso = Integer.parseInt(peso);
    }
    public void display(){
        System.out.println(this.origen+"->"+this.destino+":"+this.peso);
    }
}
class Arco {
    int xDestino;
    int yDestino;

    public Arco(int x, int y) {
        this.xDestino = x;
        this.yDestino = y;
    }

}
class Vertice {
    int x;
    int y;
    Vertice padre;
    int recorrido=0;
    LinkedList<Arco> arcos;
    boolean isPath= false;

    public Vertice( int x, int y) {
        this.x =x;
        this.y=y;
        this.arcos  = new LinkedList<>();
    }
    public void display() {
        System.out.print(this.x +","+this.y+" >>|" );
        displayArcos();
        //System.out.println("\n");
    }

    public void addEdge(Vertice destino) {
        Arco arco = new Arco(destino.x, destino.y);
        this.arcos.add(arco);
    }
    public void displayArcos() {
        for (Arco arco : arcos) {
            System.out.print(arco.xDestino + "," +arco.yDestino + " |");
        }
        System.out.println("");
    }
}
class Grafo {
    LinkedList<Vertice> vertices;
    char[][] matriz;
    public Grafo(char[][] matriz) {
        this.vertices = new LinkedList<>();
        this.matriz=matriz;
    }
    public void display(){
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                System.out.print(matriz[i][j]+" ");
            }
            System.out.println();
        }
    }
    public void limpiarPadre() {
        for (Vertice vertice : vertices) {
            vertice.padre=null;
        }
    }
    public void limpiarIsPath() {
        for (Vertice vertice : vertices) {
            vertice.isPath=false;
        }
    }
    public void limpiarRecorrido() {
        for (Vertice vertice : vertices) {
            vertice.recorrido=0;
        }
    }
    public Vertice EncontrarInicio(){
        for (Vertice vertice : this.vertices) {
            if (vertice.x==0||vertice.y==0||vertice.x==matriz.length-1||vertice.y==matriz[0].length-1) {
                return vertice;
            }
        }
        return null;
    }
    public Vertice Encontrarfinal(Vertice inicio){
        for (Vertice vertice : this.vertices) {
            if ((vertice.x==0||vertice.y==0||vertice.x==matriz.length-1||vertice.y==matriz[0].length-1)&&!vertice.equals(inicio)) {
                return vertice;
            }
        }
        return null;
    }
    public void addVertice(int x, int y) {
            Vertice v = new Vertice(x,y);
            this.vertices.add(v);

    }

    public boolean verticeExists(int x, int y) {
        for (Vertice vertice : this.vertices) {
            if (vertice.x==x&&vertice.y==y) {
                return true;
            }
        }
        return false;
    }
    public Arco buscarArco(Vertice v,int x, int y) {
        for (Arco arco : v.arcos) {
            if (arco.xDestino==x&&arco.yDestino==y) {
                return arco;
            }
        }
        return null;
    }
    public void crearVertices(char[][] laberinto) {
        for (int i = 0; i < laberinto.length; i++) {
            for (int j = 0; j < laberinto[i].length; j++) {
                if (laberinto[i][j]==' '){
                    addVertice(i,j);
                }
            }
        }

    }

    public void displayVertices() {
        for (Vertice vertice : this.vertices) {
            vertice.display();
        }
    }
    public void displayVerticesResolver() {
        for (Vertice vertice : this.vertices) {
            if (vertice.isPath){
                vertice.display();
            }
        }
    }

    public void crearArcos() {
        for (Vertice vertice :vertices) {
            if (verticeExists(vertice.x+1, vertice.y)){
                Vertice v=buscarVertice(vertice.x+1,vertice.y);
                vertice.addEdge(v);
            }
            if (verticeExists(vertice.x-1, vertice.y)){
                Vertice v=buscarVertice(vertice.x-1,vertice.y);
                vertice.addEdge(v);
            }
            if (verticeExists(vertice.x, vertice.y+1)){
                Vertice v=buscarVertice(vertice.x,vertice.y+1);
                vertice.addEdge(v);
            }
            if (verticeExists(vertice.x, vertice.y-1)){
                Vertice v=buscarVertice(vertice.x,vertice.y-1);
                vertice.addEdge(v);
            }

        }

    }

    public Vertice buscarVertice(int i, int j) {
        for (Vertice vertice : this.vertices) {
            if (vertice.x==i&&vertice.y==j) {
                return vertice;
            }
        }
        return null;
    }

    public String generaSolucion() {
        String cadena="";
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                if (verticeExists(i,j)){
                    if (buscarVertice(i,j).isPath){
                        matriz[i][j]='#';
                    }
                }
                cadena=cadena+matriz[i][j]+" ";
                System.out.print(matriz[i][j]+" ");
            }
            cadena=cadena+"\n";
            System.out.println();
        }
        return cadena;
    }

    public void grabaGrafo(String archivo_salida, String contenido_grafo) {
        try {
            BufferedWriter bf = new BufferedWriter(new FileWriter(new File(archivo_salida), false));
            bf.write(contenido_grafo);
            bf.flush();
            bf.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void DepthFirstSearch() {
        limpiarPadre();
        limpiarRecorrido();
        limpiarIsPath();
        boolean ENCONTRADO = false;
        Stack<Vertice> pila = new Stack<>();
        Vertice v = EncontrarInicio();
        Vertice inicio= EncontrarInicio();
        Vertice finals= Encontrarfinal(inicio);
        pila.push(v);
        do {
            v = pila.pop();
            if (v.equals(finals)) {
                ENCONTRADO = true;
            } else {
                agregarVecinos(pila, v);
            }
        } while (!pila.isEmpty());
        inicio.padre=null;
        System.out.println("DEPTH FIRST SEARCH");
        if (ENCONTRADO) {
            System.out.println("Existe una solución");
            mostrarCamino(finals);
            grabaGrafo("solucion.dot",generarDOT());
        } else {
            System.out.println("NO HAY solución");
        }
    }

    public void BreadthFirstSearch() {
        limpiarPadre();
        limpiarRecorrido();
        limpiarIsPath();
        boolean ENCONTRADO = false;
        Queue<Vertice> cola = new LinkedList<>();
        Vertice v = EncontrarInicio();
        Vertice inicio= EncontrarInicio();
        Vertice finals= Encontrarfinal(inicio);
        cola.add(v);
        do {
            v = cola.remove();
            if (v.equals(finals)) {
                ENCONTRADO = true;
            } else {
                agregarVecinosQ(cola, v);
            }
        } while (!cola.isEmpty());
        System.out.println("BREADTH FIRST SEARCH");
        inicio.padre=null;
        if (ENCONTRADO) {
            System.out.println("Existe una solución");
            cola=new LinkedList<>();
            v=finals;
            while (v.padre!=null){
                cola.add(v);
                v=v.padre;
            }
            cola.add(v);
            mostrarCamino(finals);
            grabaGrafo("solucion.dot",generarDOT());
        } else {
            System.out.println("NO HAY solución");
        }
    }

    public void agregarVecinosQ(Queue<Vertice> q, Vertice vertice) {
        for (Arco arco : vertice.arcos) {
            Vertice vecino = buscarVertice(arco.xDestino,arco.yDestino);
            if ((vecino.recorrido>vertice.recorrido+1)||(vecino.recorrido==0)) {
                q.add(vecino);
                vecino.recorrido=vertice.recorrido+1;
                vecino.padre=vertice;
            }
        }
    }

    public void mostrarCamino(Vertice v) {
        while (v.padre!=null){
            v.isPath=true;
            v=v.padre;
        }
        v.isPath=true;
    }

    public void agregarVecinos(Stack<Vertice> s, Vertice vertice) {
        for (Arco arco : vertice.arcos) {
            Vertice vecino = buscarVertice(arco.xDestino,arco.yDestino);
            if ((vecino.recorrido> vertice.recorrido+1)||(vecino.recorrido==0)) {
                vecino.recorrido= vertice.recorrido+1;
                vecino.padre=vertice;
                s.push(vecino);
            }
        }
    }
    public String generarDOT(){
        String cadena = "digraph G {\n";
        for (Vertice vertice : vertices) {
            for (Arco arco : vertice.arcos) {
                cadena = cadena +"\""+ vertice.x+","+vertice.y+"\"";
                cadena = cadena + " -> ";
                cadena = cadena + "\""+arco.xDestino+","+arco.yDestino+"\"";
                if (buscarVertice(arco.xDestino,arco.yDestino).isPath) {
                    cadena = cadena + " [color= \" green"+ "\"];";
                }
                cadena = cadena + "\n";
            }
        }
        cadena = cadena + "}\n";
        return cadena;
    }
}

public class GrafoListas {

    public static void main(String[] args) {
        // TODO code application logic here
        if (args.length>0) {
            int opcion=1;
            Grafo grafo=null;
            String archivo=args[0];
            Scanner teclado=new Scanner(System.in);
            do {
                if (opcion==1){
                    char[][] laberinto = cargaLaberinto(archivo);
                    grafo = new Grafo(laberinto);
                    grafo.display();
                    grafo.crearVertices(laberinto);
                    grafo.crearArcos();
                    grafo.displayVerticesResolver();
                    grafo.DepthFirstSearch();
                    grafo.BreadthFirstSearch();
                    String s = grafo.generaSolucion();
                }
                if (opcion==2){
                    grafo.DepthFirstSearch();
                    String s = grafo.generaSolucion();
                    System.out.println("EL ARCHIVO DE LA SOLUCION ES solucion.dot");
                }
                if (opcion==3){
                    grafo.BreadthFirstSearch();
                    String s = grafo.generaSolucion();
                    System.out.println("EL ARCHIVO DE LA SOLUCION ES solucion.dot");
                }
                System.out.println("1. DESEA USAR OTRO LABERINTO");
                System.out.println("2. DESEA RESOLVER USANDO DFS");
                System.out.println("3. DESEA RESOLVER USADNO BFS");
                System.out.println("4. SALIR");
                opcion=teclado.nextInt();
                if (opcion==1){
                    System.out.println("INGRESE EL NOMBRE DEL ARCHIVO");
                    archivo=teclado.next();
                }
            }while (opcion!=4);
        }
    }

    public static char[][] cargaLaberinto(String archivo) {
        int i=0;
        ArrayList<String> lineas = new ArrayList<>();
        try {

            File f = new File(archivo);
            if (!f.exists()){
                System.out.println("EL ARCHIVO NO EXISTE");
                System.exit(0);
            }
            BufferedReader b = new BufferedReader(new FileReader(f));
            String readLine = "";
            while ((readLine = b.readLine()) != null) {
                //System.out.println(readLine);
                lineas.add(readLine);
            }
            int ancho = lineas.get(0).length();
            int largo = lineas.size();
            char[][] mapa = new char[largo][ancho];
            for(String linea: lineas){
                char[] caracteres = linea.toCharArray();
                for (int j = 0; j < caracteres.length; j++) {
                    mapa[i][j] = caracteres[j];
                }
                i++;
            }
            return mapa;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
