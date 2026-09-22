package aplicacion.vista;

public class Menu {

    public void iniciar() {

        int opcion;

        do {
            System.out.println("\n----------- MENU -------------");
            System.out.println("1. Registrar pelicula");
            System.out.println("2. Registrar usuario");
            System.out.println("3. Crear watchlist");
            System.out.println("4. Anadir pelicula a watchlist");
            System.out.println("5. Consultar peliculas para adultos");
            System.out.println("6. Consultar watchlist");
            System.out.println("7. Ver historial watchlist pelicula");
            System.out.println("0. Salir");

            System.out.print("Que quieres hacer? ");

            opcion = util.Util.leerInt();

            /*
            AQUI NO, switch en el controlador
            
            switch (opcion) {

                case 1:break;

                case 2:break;

                case 3:break;

                case 4:break;

                case 5:break;

                case 6:break;

                case 7:break;

                case 0:System.out.println("Saliendo del programa..."); break;

                default: System.out.println("Opción no válida"); break; 
            } */

        } while (opcion != 0);
    }
}
