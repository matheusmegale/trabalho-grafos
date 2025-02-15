/**
 * Projeto: Trabalho Pratico da Disciplina Algoritmo em Grafos
 * 
 * Este programa implementa operacões basicas para manipulacao de grafos, incluindo a leitura
 * de dados de vertices e arestas, construcao da matriz de adjacencias, verificacao de 
 * conectividade, e impressao de informacões do grafo. Pode ser utilizado para estudar
 * propriedades de grafos tanto direcionados quanto nao direcionados.
 * 
 *  * Funcionalidades Obrigatorias:
 * - Verificar se um grafo eh conexo (para o caso de grafos orientados, verificar conectividade fraca.)   
 * - Verificar se um grafo nao orientado eh bipartido.   
 * - Verificar se um grafo qualquer eh Euleriano.   
 * - Verificar se um grafo possui ciclo.   
 * - Calcular a quantidade de componentes conexas em um grafo nao orientado.   
 * - Calcular a quantidade de componentes fortemente conexas em um grafo orientado.   
 * - Imprimir os vertices de articulacao de um grafo nao orientado (priorizar a ordem lexicografica dos vertices).    
 * - Calcular quantas arestas ponte possui um grafo nao orientado.    
 * - Imprimir a arvore em profundidade (priorizando a ordem lexicografica dos vertices; 0 eh a origem). Voce deve imprimir o identificador das arestas. Caso o grafo seja desconexo, considere apenas a arvore com a raiz 0.   
 * - Arvore de largura (priorizando a ordem lexicografica dos vertices; 0 eh a origem). Voce deve imprimir o identificador das arestas. Caso o grafo seja desconexo, considere apenas a arvore com a raiz 0.    
 * - Calcular o valor final de uma arvore geradora minima (para grafos nao orientados).    
 * - Imprimir a ordem os vertices em uma ordenação topologica. Esta funcao nao fica disponivel em grafos nao direcionado. Deve-se priorizar a ordem lexicografica dos vertices.   
 * - Valor do caminho minimo entre dois vertices (para grafos nao orientados com pelo menos um peso diferente nas arestas).  0 eh a origem; n-1 eh o destino.   
 * - Valor do fluxo maximo para grafos direcionados. 0 eh a origem; n-1 eh o destino.   
 * - Fecho transitivo para grafos direcionados.  Deve-se priorizar a ordem lexicografica dos vertices; 0 eh o vertice escolhido.
 * 
 * @version 1.0
 * @date 21/08/2024
 * 
 * @authors
 * - Eduardo Ruan Guimaraes Fonseca (eduardo.fonseca1@estudante.ufla.br)
 * - Matheus de Paula Megale (matheus.megale@estudante.ufla.br)
 * - Renan Augusto Da Silva (renan.silva6@estudante.ufla.br)
 * 
 * @since 1.0
 */

import java.util.Scanner;
import java.util.List;
import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Stack;

public class Main {
    /**
     * Ponto de entrada da aplicacao. Este metodo configura e inicializa os componentes
     * necessarios para trabalhar com grafos, incluindo a leitura de dados de entrada, 
     * construcao da matriz de adjacencias e da lista de arestas, e a verificacao se o grafo  direcionado ou nao.
     *
     * @param args Argumentos da linha de comando (nao utilizados neste programa).
     * @throws Exception Se ocorrer um erro durante a execucao.
     */
    public static void main(String[] args) throws Exception {
        // cria um objeto Scanner para ler a entrada do usuario
        Scanner scanner = new Scanner(System.in);

        // declaracao de variaveis para armazenar o numero de vertices, arestas e se o grafo  direcionado ou nao
        /*
         * numero de vertices
         */
        int vertices;
        /*
         * numero de arestas
         */
        int arestas;
        /*
         * direcionado ou nao direcionado
         */
        String direcionado_ou_nao_direcionado;

        List<Integer> sequenciaMetodos = new ArrayList<>();
        
        String entrada = scanner.nextLine();
        for (String valor : entrada.split(" ")) {
            sequenciaMetodos.add(Integer.parseInt(valor));
        }

        // declaracao de variaveis para a matriz de adjacencias e a lista de arestas
        int[][] matriz_adjacencias;
        List<Integer[]> lista_de_arestas;

        // le o numero de vertices e arestas do usuario
        vertices = scanner.nextInt();
        arestas = scanner.nextInt();

        // inicializa a matriz de adjacencias com o tamanho apropriado
        matriz_adjacencias = new int[vertices][vertices];
        
        // inicializa a lista de arestas com o tamanho apropriado
        lista_de_arestas = new ArrayList<>();

        // preenche a lista de arestas com arrays de tamanho 4 (ID da aresta, origem, destino, peso)
        for(int i = 0; i < arestas; i++){
            lista_de_arestas.add(new Integer[4]);
        }

        // inicializa a matriz de adjacencias com um valor que indica ausencia de aresta
        for (int i = 0; i < vertices; i++) {
            for (int j = 0; j < vertices; j++) {
                matriz_adjacencias[i][j] = Integer.MAX_VALUE; // Usamos Integer.MAX_VALUE para indicar ausencia de aresta
            }
        }

        // le se o grafo  direcionado ou nao
        direcionado_ou_nao_direcionado = scanner.next();

        // le os dados das arestas e atualiza a matriz de adjacencias e a lista de arestas
        for (int i = 0; i < arestas; i++) {
            int id_aresta = scanner.nextInt(); // identificador da aresta
            int u = scanner.nextInt();         // vertice de origem
            int v = scanner.nextInt();         // vertice de destino
            int peso = scanner.nextInt();      // peso da aresta
            
            // atualiza a matriz de adjacencias
            matriz_adjacencias[u][v] = peso;
            
            // adiciona a aresta a lista de arestas
            lista_de_arestas.get(i)[0] = id_aresta;
            lista_de_arestas.get(i)[1] = u;
            lista_de_arestas.get(i)[2] = v;
            lista_de_arestas.get(i)[3] = peso;
            
            // se o grafo nao for direcionado, atualiza a matriz de adjacencias
            // para refletir a bidirecionalidade (aresta de v para u com o mesmo peso)
            if (direcionado_ou_nao_direcionado.equals("nao_direcionado")) {
                matriz_adjacencias[v][u] = peso;
            }
        }

        // chama os metodos de acordo com a sequencia fornecida
        for (int metodo : sequenciaMetodos) {
            switch (metodo) {
                case 0:
                    // verifica se o grafo eh conexo
                    System.out.println(eh_Conexo(vertices, direcionado_ou_nao_direcionado, matriz_adjacencias));
                    break;
                case 1:
                    // verifica se o grafo eh bipartido
                    System.out.println(eh_Bipartido(vertices, matriz_adjacencias));
                    break;
                case 2:
                    // verifica se o grafo eh eureliano
                    System.out.println(eh_Euleriano(vertices, direcionado_ou_nao_direcionado, matriz_adjacencias));
                    break;
                case 3:
                    // verifica se o grafo possui ciclos
                    System.out.println(possui_ciclo(vertices, matriz_adjacencias, direcionado_ou_nao_direcionado));
                    break;
                case 4:
                    // conta os componentes conexos do grafo
                    System.out.println(contar_componentes_conexas(vertices, direcionado_ou_nao_direcionado, matriz_adjacencias));
                    break;
                case 5:
                    // conta os componentes fortemente conexos do grafo e imprime o resultado
                    Integer[] numero_de_componentes = contar_componentes_fortemente_conexas(vertices, direcionado_ou_nao_direcionado, matriz_adjacencias);
                    System.out.println(numero_de_componentes[0]);
                    break;
                case 6:
                    // lista e imprime os vertices de articulacao do grafo
                    List<Integer> vertices_de_articulacao = listar_vertices_de_articulacao(vertices, direcionado_ou_nao_direcionado, matriz_adjacencias);
                    for(int vertice : vertices_de_articulacao){
                        System.out.print(vertice + " ");
                    }
                    System.out.println();
                    break;
                case 7:
                    // conta e imprime as arestas pontes do grafo
                    int quantidade_arestas_pontes = contar_arestas_pontes(vertices, direcionado_ou_nao_direcionado, matriz_adjacencias);
                    System.out.println(quantidade_arestas_pontes);
                    break;
                case 8:
                    // gera e imprime a arvore de profundidade, mostrando os identificadores das arestas
                    List<Integer> arvore_de_profundidade = gerar_arvore_de_profundidade(vertices, direcionado_ou_nao_direcionado, matriz_adjacencias, lista_de_arestas);
                    for(int id_aresta : arvore_de_profundidade){
                        System.out.print(id_aresta + " ");
                    }
                    System.out.println();
                    break;
                case 9:
                    // gera e imprime a arvore de largura, mostrando os identificadores das arestas
                    List<Integer> arvore_de_largura = gerar_arvore_de_largura(vertices, direcionado_ou_nao_direcionado, matriz_adjacencias, lista_de_arestas);
                    for(int id_aresta : arvore_de_largura){
                        System.out.print(id_aresta + " ");
                    }
                    System.out.println();
                    break;
                case 10:
                    // gera e imprime o valor da arvore geradora minima
                    System.out.println(gerar_arvore_geradora_minima(vertices, direcionado_ou_nao_direcionado, matriz_adjacencias, lista_de_arestas));
                    break;
                case 11:
                    // gera e imprime a ordenacao topologica do grafo
                    List<Integer> ordenacao_topologica = gerar_ordenacao_topologica(vertices, direcionado_ou_nao_direcionado, matriz_adjacencias);
                    for(int vertice : ordenacao_topologica){
                        System.out.print(vertice + " ");
                    }
                    System.out.println();
                    break;
                case 12:
                    // verifia o valor minimo entre dois vertices
                    int valor_minimo_entre_dois_vertices = bellman_ford(vertices, direcionado_ou_nao_direcionado, arestas, matriz_adjacencias);
                    System.out.println(valor_minimo_entre_dois_vertices);
                    break;
                case 13:
                    // verifica o fluxo maximo entre 2 vertices
                    System.out.println(ford_fulkerson(vertices, direcionado_ou_nao_direcionado, arestas, matriz_adjacencias, lista_de_arestas));
                    break;
                case 14:
                    // lista o fecho transitivo de um vertice
                    List<Integer> fecho_transitivo = fecho_transitivo(vertices, direcionado_ou_nao_direcionado, matriz_adjacencias);
                    for(int vertice : fecho_transitivo){
                        System.out.print(vertice + " ");
                    }
                    break;

            }
        }

        // // PRINTAR MATRIZ DE ADJACENCIAS EXTRA
        // print_grafo(vertices, matriz_adjacencias);

        // // PRINTAR LISTA DE ARESTAS EXTRA
        // print_arestas(lista_de_arestas);

        // // PRINTAR COMPONENTES CONEXAS PARA GRAFOS NAO DIRECIONADOS EXTRA
        // listar_componentes_conexas_EXTRA(vertices, direcionado_ou_nao_direcionado, matriz_adjacencias);

        // // PRINTAR COMPONENTES FORTEMENTE CONCEXAS PARA GRAFOS DIRECIONADOS EXTRA
        // Integer[] quantidade_de_componetes = listar_componentes_fortemente_conexas_EXTRA(vertices, direcionado_ou_nao_direcionado, matriz_adjacencias);
        // System.out.println("Quantidade de componentes fortemente conexos: " + quantidade_de_componetes[0]);

        // // PRINTAR TRILHA EULERIANA EXTRA
        // List<Integer> trilha_euleriana = trilha_euleriana_EXTRA(vertices, direcionado_ou_nao_direcionado, matriz_adjacencias);
        // System.out.print("Trilha euleriana: ");
        // for(int vertice : trilha_euleriana){
        //     System.out.print(vertice + " ");
        // }
        // System.out.println();

        // //PRINTAR OS ID's DA ARVORE GERADORA MINIMA
        // List<Integer> arvore_geradora_minima = gerar_arvore_geradora_minima_EXTRA(vertices, direcionado_ou_nao_direcionado, matriz_adjacencias, lista_de_arestas);
        // System.out.print("Id's das arestas da arvore geradora minima: ");
        // for(int vertice : arvore_geradora_minima){
        //     System.out.print(vertice + " ");
        // }
        // System.out.println();

        scanner.close();
    }

    /**
     * Imprime a matriz de adjacencias do grafo.
     * Este metodo  utilizado para fins de depuracao.
     *
     * @param vertices O numero total de vertices no grafo.
     * @param matriz_adjacencias A matriz de adjacencias representando o grafo, onde:
     *                           - matriz_adjacencias[i][j] representa o peso da aresta entre os vertices i e j.
     *                           - Integer.MAX_VALUE  usado para indicar a ausencia de uma aresta entre os vertices.
     */
    public static void print_grafo(int vertices, int[][] matriz_adjacencias) {
        // exibe um titulo para a matriz de adjacencias
        System.out.println("Matriz de Adjacencias:");
        
        // itera sobre todas as linhas da matriz de adjacencias
        for (int i = 0; i < vertices; i++) {
            // itera sobre todas as colunas da matriz de adjacencias
            for (int j = 0; j < vertices; j++) {
                // verifica se o valor na posicao (i, j)  igual a Integer.MAX_VALUE
                // integer.MAX_VALUE  usado para representar a ausencia de uma aresta entre os vertices i e j
                if (matriz_adjacencias[i][j] == Integer.MAX_VALUE) {
                    // imprime "INF" para indicar que nao ha aresta entre os vertices i e j
                    System.out.print("INF ");
                } else {
                    // imprime o peso da aresta entre os vertices i e j
                    System.out.print(matriz_adjacencias[i][j] + " ");
                }
            }
            System.out.println();
        }
    }

    /**
     * Imprime a lista de arestas do grafo.
     *
     * @param lista_de_arestas Uma lista onde cada elemento  um array de inteiros
     *                         representando uma aresta no grafo. Cada array contm:
     *                         - lista[0]: ID da aresta.
     *                         - lista[1]: vertice de origem (U).
     *                         - lista[2]: vertice de destino (V).
     *                         - lista[3]: Peso da aresta.
     */
    public static void print_arestas(List<Integer[]> lista_de_arestas) {
        // exibe um titulo para a lista de arestas
        System.out.println("Lista de Arestas:");
        
        // itera sobre cada array de informacões de aresta na lista de arestas
        for(Integer[] lista : lista_de_arestas){
            // imprime o ID da aresta, os vertices de origem (U) e destino (V), e o peso da aresta
            System.out.println("ID: " + lista[0] + " U: " + lista[1] + " V: " + lista[2] + " Peso: " + lista[3]);
        }
    }

    /**
     * Cria e inicializa um array para marcar se os vertices foram visitados.
     *
     * @param vertices O numero total de vertices no grafo.
     * @return Um array onde cada elemento representa o estado de visitacao de um vertice.
     *         0 indica que o vertice nao foi visitado, e 1 indica que foi visitado.
     *         Inicialmente, todos os vertices sao marcados como nao visitados (0).
     */
    public static Integer[] inicializar_vertices_com_zero(int vertices){
        // cria um array para armazenar o estado de visita dos vertices
        Integer[] visitados = new Integer[vertices];
        
        // inicializa todos os elementos do array como 0
        // 0 indica que o vertice correspondente ainda nao foi visitado
        for(int i = 0; i < vertices; i++) {
            visitados[i] = 0; // Inicializa com zero, pois nao foram visitados
        }
        
        // retorna o array com todos os valores inicializados
        return visitados;
    }

    /**
     * Converte uma matriz de adjacencias de um grafo direcionado em uma matriz de adjacencias 
     * de um grafo nao direcionado.
     *
     * @param vertices O numero total de vertices no grafo.
     * @param matriz_adjacencias A matriz de adjacencias representando o grafo direcionado.
     * @return Uma nova matriz de adjacencias representando o grafo como nao direcionado.
     */
    public static int[][] converte_matriz_direcionada_para_nao_direcionada(int vertices, int[][] matriz_adjacencias) {
        // cria uma nova matriz para armazenar a representacao nao direcionada do grafo
        int[][] matriz_nao_direcionada = new int[vertices][vertices];
        
        // inicializa a nova matriz com Integer.MAX_VALUE para indicar ausencia de aresta
        for (int i = 0; i < vertices; i++) {
            for (int j = 0; j < vertices; j++) {
                matriz_nao_direcionada[i][j] = Integer.MAX_VALUE;
            }
        }
    
        // preenche a nova matriz com os valores da matriz direcionada
        for (int i = 0; i < vertices; i++) {
            for (int j = 0; j < vertices; j++) {
                // verifica se ha uma aresta na direcao i -> j
                if (matriz_adjacencias[i][j] != Integer.MAX_VALUE) {
                    // adiciona a aresta na direcao i -> j na nova matriz
                    matriz_nao_direcionada[i][j] = matriz_adjacencias[i][j];
                    // adiciona a aresta na direcao j -> i para tornar a matriz nao direcionada
                    matriz_nao_direcionada[j][i] = matriz_adjacencias[i][j];
                }
            }
        }
    
        // retorna a matriz de adjacencias convertida para um grafo nao direcionado
        return matriz_nao_direcionada;
    }

    /**
     * Realiza uma busca em profundidade (DFS) para verificar a conectividade do grafo.
     *
     * @param vertice_inicial O vertice a partir do qual a DFS sera iniciada.
     * @param vertices O numero total de vertices no grafo.
     * @param matriz_adjacencias A matriz de adjacencias representando o grafo.
     * @param visitados Um array que rastreia quais vertices ja foram visitados durante a DFS.
     */
    public static void dfs_verificar_conexidade(int vertice_inicial, int vertices, int[][] matriz_adjacencias, Integer[] visitados) {
        visitados[vertice_inicial] = 1;
        for (int i = 0; i < vertices; i++) {
            if (matriz_adjacencias[vertice_inicial][i] != Integer.MAX_VALUE && visitados[i] == 0) {
                dfs_verificar_conexidade(i, vertices, matriz_adjacencias, visitados);
            }
        }
    }

    /**
     * Verifica se um grafo  conexo.
     *
     * @param vertices O numero de vertices no grafo.
     * @param direcionado_ou_nao_direcionado Uma string que indica se o grafo  "direcionado" ou "nao_direcionado".
     * @param matriz_adjacencias A matriz de adjacencias representando o grafo.
     * @return Retorna 1 se o grafo for conexo, ou 0 se for desconexo.
     */
    public static int eh_Conexo(int vertices, String direcionado_ou_nao_direcionado, int[][] matriz_adjacencias){
        // inicializar os visitados com 0
        Integer[] visitados = inicializar_vertices_com_zero(vertices); 
    
        // variavel para verificar quantas vezes a dfs foi chamada
        int quantas_vezes_chamou_dfs = 0;

        if (direcionado_ou_nao_direcionado.equals("nao_direcionado")) {
            // for para percorrer a lista de vertices visitados
            // se encontrar um vertice que ainda nao foi visitado depois
            // da primeira chamada da dfs, entao o grafo eh desconexo
            for(int i = 0; i < vertices; i++) {
                if(visitados[i] == 0) {
                    // se chamar a dfs mais de uma vez, entao eh desconexo
                    quantas_vezes_chamou_dfs++;
                    dfs_verificar_conexidade(i, vertices, matriz_adjacencias, visitados);
                }
            }
        } else {
            // se o grafo eh direcionado e a conversao dele em em grafo nao-direcionado ainda 
            // resultar em um grafo conexo, entao ele eh fracamente conexo
            int[][] matriz_nao_direcionada = converte_matriz_direcionada_para_nao_direcionada(vertices, matriz_adjacencias);

            // for para percorrer a lista de vertices visitados
            // se encontrar um vertice que ainda nao foi visitado depois
            // da primeira chamada da dfs, entao o grafo eh desconexo
            for(int i = 0; i < vertices; i++) {
                if(visitados[i] == 0) {
                    // se chamar a dfs mais de uma vez, entao eh desconexo
                    quantas_vezes_chamou_dfs++;
                    dfs_verificar_conexidade(i, vertices, matriz_nao_direcionada, visitados);
                }
            }

        }

        // esse if serve tanto para direcionado quanto para nao-direcionado
        if (quantas_vezes_chamou_dfs > 1) {
            return 0; // chamou a dfs mais de uma vez, entao eh desconexo
        }
        return 1; // chamou a dfs uma vez, entao eh conexo
    }

    /**
     * Realiza uma busca em largura (BFS) para verificar se o grafo  bipartido.
     * 
     * @param vertice_inicial O vertice de inicio para a busca em largura.
     * @param cores Um array que armazena a cor de cada vertice. O valor -1 indica que o vertice ainda nao foi colorido.
     * @param vertices O numero total de vertices no grafo.
     * @param matriz_adjacencias A matriz de adjacencias representando o grafo. Onde matriz_adjacencias[i][j] representa
     *                            o peso da aresta entre os vertices i e j. Um valor de Integer.MAX_VALUE indica a ausencia de aresta.
     * @return Retorna `true` se o grafo  bipartido e `false` caso contrario.
     * 
     * @note Este metodo  chamado por `eh_Bipartido` para verificar cada componente conectado do grafo.
     */
    public static boolean bfs_para_bipartido(int vertice_inicial, int[] cores, int vertices, int[][] matriz_adjacencias) {
        // Fila para bfs
        Queue<Integer> fila = new LinkedList<>();
        fila.add(vertice_inicial);
        // Comeca colorindo com 0
        cores[vertice_inicial] = 0;

        while (!fila.isEmpty()) {
            int u = fila.poll();
            for (int v = 0; v < vertices; v++) {
                // existe uma aresta entre u e v
                if (matriz_adjacencias[u][v] != Integer.MAX_VALUE) {
                    if (cores[v] == -1) {
                        // colore com a cor oposta
                        cores[v] = 1 - cores[u];
                        fila.add(v);
                    } else if (cores[v] == cores[u]) {
                        // encontra dois vertices adjacentes com a mesma cor
                        return false;
                    }
                }
            }
        }
        // eh bipartido
        return true;
    }

    /**
     * Verifica se um grafo  bipartido.
     * 
     * @param vertices O numero de vertices no grafo.
     * @param matriz_adjacencias A matriz de adjacencias do grafo, onde matriz_adjacencias[i][j] representa
     *                            o peso da aresta entre os vertices i e j. Um valor de Integer.MAX_VALUE
     *                            indica a ausencia de aresta.
     * @return Retorna 1 se o grafo for bipartido e 0 caso contrario.
     * 
     * @note O metodo  aplicavel tanto para grafos orientados quanto nao-orientados. Para grafos orientados,
     *       o metodo verifica se  possivel colorir os vertices com duas cores de forma que as arestas
     *       dirigidas nao violam a condicao de biparticao.
     * 
     * @see #bfs_para_bipartido(int, int[], int, int[][]) Para a implementacao da busca em largura usada
     *      para verificar a biparticao.
     */
    public static int eh_Bipartido(int vertices, int[][] matriz_adjacencias){
        // inicializa todas as cores com -1 (nao colorido)
        int[] cores = new int[vertices];
        for(int i = 0; i < vertices; i++){
            cores[i] = -1;
        }

        // verifica cada componente conectado
        for (int i = 0; i < vertices; i++) {
            // vetice ainda nao colorido
            if (cores[i] == -1) {
                if (!bfs_para_bipartido(i, cores, vertices, matriz_adjacencias)) {
                    return 0;
                }
            }
        }
        return 1;
    }

    /**
     * Verifica se um grafo  euleriano.
     * 
     * @param vertices O numero de vertices no grafo.
     * @param direcionado_ou_nao_direcionado Uma string que indica se o grafo  direcionado ou nao direcionado. 
     *                                      Os valores possiveis sao "direcionado" e "nao_direcionado".
     * @param matriz_adjacencias A matriz de adjacencias do grafo, onde matriz_adjacencias[i][j] representa
     *                            o peso da aresta entre os vertices i e j. Um valor de Integer.MAX_VALUE indica
     *                            a ausencia de aresta.
     * @return Retorna 1 se o grafo  euleriano e 0 caso contrario.
     * 
     * @note 
     * - Para grafos nao direcionados, o metodo verifica se todos os vertices possuem grau par.
     * - Para grafos direcionados, o metodo verifica se o grau de entrada e o grau de saida de cada vertice sao iguais.
     * 
     * @see #eh_Conexo(int, String, int[][]) Para verificar se o grafo  conexo.
     */
    public static int eh_Euleriano(int vertices, String direcionado_ou_nao_direcionado, int[][] matriz_adjacencias){
        if (eh_Conexo(vertices, direcionado_ou_nao_direcionado, matriz_adjacencias) == 0) {
            // se o grafo eh desconexo, entao nao pode ser euleriano
            return 0; 
        }
        
        if(direcionado_ou_nao_direcionado.equals("nao_direcionado")){
            // array para armazenar o grau dos vertices 
            int[] grau_do_vertice = new int[vertices]; 
            // inicializa cada posicao com 0
            for(int i = 0; i < vertices; i++){  
                grau_do_vertice[i] = 0;
            }

            for(int i = 0; i < vertices; i++){
                for(int j = 0; j < vertices; j++){
                    if(matriz_adjacencias[i][j] != Integer.MAX_VALUE){
                        // atualiza o grau do vertice
                        grau_do_vertice[i]++; 
                    }

                }
                // verifica se o grau eh impar, se for, entao nao eh euleriano
                if (grau_do_vertice[i] % 2 != 0) { 
                    return 0;
                }
            }
        }
        else{
            // para grafos direcionados, precisamos armazenar o grau de entrada e saida de cada vertice
            // array para armazenar o grau de saida dos vertices
            int[] grau_saida = new int[vertices]; 
            // array para armazenar o grau de entrada dos vertices
            int[] grau_entrada = new int[vertices];
             
            // inicializa os graus com 0 
            for(int i = 0; i < vertices; i++){ 
                grau_entrada[i] = 0;
                grau_saida[i] = 0;
            }
            
            // atualiza os graus de entrada e saida de cada vertice
            for(int i = 0; i < vertices; i++){
                for(int j = 0; j < vertices; j++){
                    if(matriz_adjacencias[i][j] != Integer.MAX_VALUE){
                        grau_saida[i]++; 
                        grau_entrada[j]++; 
                    }
                }
            }

            // verifica se o grafo eh pseudossimetrico, se nao for, entao nao eh euleriano 
            for (int k = 0; k < vertices; k++) {
                if(grau_entrada[k] != grau_saida[k]){
                    return 0;
                }
            }
        }
        return 1;
    }

    /**
     * Verifica a presenca de ciclos em um grafo utilizando a busca em profundidade (DFS).
     * 
     * @param vertice_inicial O vertice inicial para comecar a busca em profundidade.
     * @param vertices O numero total de vertices no grafo.
     * @param matriz_adjacencias A matriz de adjacencias do grafo, onde `matriz_adjacencias[i][j]` 
     *                            representa o peso da aresta entre os vertices `i` e `j`. Um valor de 
     *                            `Integer.MAX_VALUE` indica a ausencia de aresta.
     * @param direcionado_ou_nao_direcionado Uma string que indica se o grafo  direcionado ou nao 
     *                                        direcionado. Os valores possiveis sao "direcionado" 
     *                                        e "nao_direcionado".
     * @param visitados Array que marca o estado de visita de cada vertice. Os estados sao:
     *                  - 0: Nao visitado
     *                  - 1: Em processamento
     *                  - 2: Completamente visitado
     * @param pai Array que armazena o pai de cada vertice na arvore de busca.
     * @return Retorna 1 se um ciclo for detectado e 0 caso contrario.
     * 
     * @note 
     * - Em grafos nao direcionados, um ciclo  detectado quando um vertice ja visitado  encontrado 
     *   e nao  o pai do vertice atual.
     * - Em grafos direcionados, um ciclo  detectado quando um vertice ainda esta em processamento 
     *   (marcado como 1).
     * - Para grafos direcionados, a funcao marca o vertice como completamente visitado (2) apos 
     *   processar todos os seus vizinhos.
     */
    public static int dfs_para_verificar_ciclo(int vertice_inicial, int vertices, int[][] matriz_adjacencias, String direcionado_ou_nao_direcionado, Integer[] visitados, Integer[] pai) {
        if (direcionado_ou_nao_direcionado.equals("nao_direcionado")){
            // em um grafo nao-direcionado, havera ciclo se um vertice tiver ligacao com outro vertice
            // ja visitado (1) que nao seja seu pai
            visitados[vertice_inicial] = 1;
            for (int i = 0; i < vertices; i++) {
                if (matriz_adjacencias[vertice_inicial][i] != Integer.MAX_VALUE) {
                    if (visitados[i] == 0) {
                        pai[i] = vertice_inicial;
                        if (dfs_para_verificar_ciclo(i, vertices, matriz_adjacencias, direcionado_ou_nao_direcionado, visitados, pai) == 1) {
                            // propaga o ciclo detectado, eh neste return que eu volto para a funcao que chamou a dfs
                            return 1;
                        }
                    } else if (visitados[i] == 1 && i != pai[vertice_inicial]) {
                        // tem ciclo, encontrou um vertice ja visitado que nao eh seu pai
                        return 1;
                    }
                }
            }
        } else {
            // em um grafo direcionado, havera ciclo se um vertice tiver ligacao com outro vertice
            // ainda em processamento (1), e nao havera ciclo se ele tiver ligacao com outro vertice
            // completamente visitado (2)
            // BRANCO = 0, CINZA = 1, PRETO = 2
            visitados[vertice_inicial] = 1;
            for (int i = 0; i < vertices; i++) {
                if (matriz_adjacencias[vertice_inicial][i] != Integer.MAX_VALUE) {
                    if (visitados[i] == 0) {
                        if (dfs_para_verificar_ciclo(i, vertices, matriz_adjacencias, direcionado_ou_nao_direcionado, visitados, pai) == 1) {
                            // propaga o ciclo detectado, eh neste return que eu volto para a funcao que chamou a dfs
                            return 1;
                        }
                    } else if (visitados[i] == 1) {
                        // tem ciclo, encontrou um vertice ainda em processamento
                        return 1;
                    }
                }
            }
            visitados[vertice_inicial] = 2;
        }
        
        // nao tem ciclo
        return 0;
    }
    
    /**
     * Verifica se um grafo possui ciclos utilizando a busca em profundidade (DFS).
     * 
     * @param vertices O numero total de vertices no grafo.
     * @param matriz_adjacencias A matriz de adjacencias do grafo, onde `matriz_adjacencias[i][j]` 
     *                            representa o peso da aresta entre os vertices `i` e `j`. Um valor de 
     *                            `Integer.MAX_VALUE` indica a ausencia de aresta.
     * @param direcionado_ou_nao_direcionado Uma string que indica se o grafo  direcionado ou nao 
     *                                        direcionado. Os valores possiveis sao "direcionado" 
     *                                        e "nao_direcionado".
     * @return Retorna 1 se um ciclo for detectado e 0 caso contrario.
     * 
     * @note
     * - O metodo inicializa arrays para marcar o estado dos vertices (`visitados`) e para armazenar 
     *   o pai de cada vertice (`pai`).
     * - Verifica todos os componentes conectados do grafo, utilizando a busca em profundidade a partir 
     *   de cada vertice nao visitado.
     * - Caso um ciclo seja detectado em qualquer componente, o metodo retorna 1 e encerra a busca.
     * 
     * @see #dfs_para_verificar_ciclo(int, int, int[][], String, Integer[], Integer[]) Para detalhes 
     *      sobre a implementacao da busca em profundidade para a deteccao de ciclos.
     */
    public static int possui_ciclo(int vertices, int[][] matriz_adjacencias, String direcionado_ou_nao_direcionado) {
        Integer[] visitados = inicializar_vertices_com_zero(vertices);
        Integer[] pai = new Integer[vertices];
        // inicializa os pais com -1
        for (int i = 0; i < vertices; i++) {
            pai[i] = -1;
        }

        for (int i = 0; i < vertices; i++) {
            // Verifica cada componente conectado
            if (visitados[i] == 0) {
                if (dfs_para_verificar_ciclo(i, vertices, matriz_adjacencias, direcionado_ou_nao_direcionado, visitados, pai) == 1) {
                    // ciclo detectado
                    return 1;
                }
            }
        }

        // nao tem ciclo
        return 0;
    }

    /**
     * Realiza uma busca em profundidade (DFS) para explorar uma componente conexa de um grafo nao direcionado.
     * 
     * @param vertice_inicial O vertice a partir do qual a busca em profundidade deve iniciar.
     * @param vertices O numero total de vertices no grafo.
     * @param matriz_adjacencias A matriz de adjacencias do grafo, onde `matriz_adjacencias[i][j]` 
     *                            representa o peso da aresta entre os vertices `i` e `j`. Um valor de 
     *                            `Integer.MAX_VALUE` indica a ausencia de aresta.
     * @param visitados Um array que marca se um vertice foi visitado ou nao. O valor `0` indica nao visitado,
     *                   e `1` indica visitado.
     * 
     * @see #contar_componentes_conexas(int, String, int[][]) metodo principal que conta o numero de componentes conexas
     *      e chama este metodo para explorar cada componente.
     */
    public static void dfs_para_contar_componentes_conexas(int vertice_inicial, int vertices, int[][] matriz_adjacencias, Integer[] visitados) {
        visitados[vertice_inicial] = 1;
        for (int i = 0; i < vertices; i++) {
            if (matriz_adjacencias[vertice_inicial][i] != Integer.MAX_VALUE && visitados[i] == 0) {
                dfs_para_contar_componentes_conexas(i, vertices, matriz_adjacencias, visitados);
            }
        }
    }

    /**
     * Conta o numero de componentes conexas em um grafo nao direcionado.
     * 
     * @param vertices O numero total de vertices no grafo.
     * @param direcionado_ou_nao_direcionado Uma string que indica se o grafo  direcionado ou nao direcionado. 
     *                                      O valor esperado  "nao_direcionado" para grafos nao direcionados.
     * @param matriz_adjacencias A matriz de adjacencias do grafo, onde `matriz_adjacencias[i][j]` 
     *                            representa o peso da aresta entre os vertices `i` e `j`. Um valor de 
     *                            `Integer.MAX_VALUE` indica a ausencia de aresta.
     * 
     * @return A quantidade de componentes conexas no grafo nao direcionado. Para grafos direcionados, 
     *         o metodo retorna -1.
     * 
     * @note
     * - O metodo inicializa um array de visitados e uma variavel para contar as componentes conexas.
     * - Percorre todos os vertices, e para cada vertice nao visitado, incrementa o contador de componentes 
     *   conexas e chama o metodo `dfs_para_contar_componentes_conexas` para explorar toda a componente conexa.
     * - Para grafos direcionados, a contagem de componentes conexas nao  realizada e o metodo retorna -1.
     * 
     * @see #dfs_para_contar_componentes_conexas(int, int, int[][], Integer[]) metodo auxiliar que realiza a 
     *      busca em profundidade para marcar todos os vertices de uma componente conexa.
     */
    public static int contar_componentes_conexas(int vertices, String direcionado_ou_nao_direcionado, int[][] matriz_adjacencias) {
        Integer[] visitados = inicializar_vertices_com_zero(vertices);
        int quantidade_de_componentes_conexas = 0;

        if (direcionado_ou_nao_direcionado.equals("nao_direcionado")) {
            for(int i = 0; i < vertices; i++) {
                if(visitados[i] == 0) {
                    // o numero de vezes que chamar a dfs eh o numero de componentes conexas
                    quantidade_de_componentes_conexas++;
                    dfs_para_contar_componentes_conexas(i, vertices, matriz_adjacencias, visitados);
                }
            }
        }
        else{
            // nao verificamos para grafos direcionados
            return -1;  
        }
        return quantidade_de_componentes_conexas;
    }

    /**
     * Realiza uma busca em profundidade (DFS) para identificar componentes fortemente conectados em um grafo dirigido
     * utilizando o algoritmo de Tarjan.
     * 
     * @param vertice_inicial O vertice a partir do qual a busca em profundidade deve iniciar.
     * @param vertices O numero total de vertices no grafo.
     * @param matriz_adjacencias A matriz de adjacencias do grafo, onde `matriz_adjacencias[i][j]` 
     *                            representa a existencia de uma aresta entre os vertices `i` e `j`. Um valor diferente
     *                            de `Integer.MAX_VALUE` indica a presenca de uma aresta.
     * @param visitados Um array que marca se um vertice foi visitado ou nao. O valor `0` indica nao visitado,
     *                   e `1` indica visitado.
     * @param tempo_descoberta Um array que armazena o tempo em que cada vertice foi descoberto durante a DFS.
     * @param low Um array que armazena o menor valor de `tempo_descoberta` acessivel a partir de cada vertice.
     * @param estaNaPilha Um array que indica se um vertice esta atualmente na pilha.
     * @param pilha A pilha usada para rastrear os vertices durante a DFS.
     * @param tempo Um array de tamanho 1 que mantm o tempo global de descoberta dos vertices.
     * @param numero_de_componentes Um array de tamanho 1 que conta o numero total de componentes fortemente conectados
     *                              encontrados durante a execucao da DFS.
     * 
     * @see #encontrar_componentes_fortemente_conectados(int, int[][]) metodo principal que inicia a busca para encontrar
     *      todas as componentes fortemente conectadas no grafo e chama este metodo para explorar cada componente.
     */
    public static void dfs_tarjan_componentes_fortemente_conexos(int vertice_inicial, int vertices, int[][] matriz_adjacencias, Integer[] visitados, Integer[] tempo_descoberta, Integer[] low, boolean[] estaNaPilha, Stack<Integer> pilha, Integer[] tempo, Integer[] numero_de_componentes){
        // marca o vertice inicial como visitado e inicializa seus tempos de descoberta e low
        visitados[vertice_inicial] = 1;
        tempo_descoberta[vertice_inicial] = tempo[0];
        low[vertice_inicial] = tempo[0];
        tempo[0]++;
        pilha.push(vertice_inicial);
        estaNaPilha[vertice_inicial] = true;
        // explora todos os vertices adjacentes ao vertice inicial
        for(int i = 0; i < vertices; i++){
            if(matriz_adjacencias[vertice_inicial][i] != Integer.MAX_VALUE){
                if(visitados[i] == 0){
                    // recorre no vertice adjacente
                    dfs_tarjan_componentes_fortemente_conexos(i, vertices, matriz_adjacencias, visitados, tempo_descoberta, low, estaNaPilha, pilha, tempo, numero_de_componentes);
                    // atualiza o valor de low para o vertice inicial
                    low[vertice_inicial] = Math.min(low[vertice_inicial], low[i]);
                }
                else if (estaNaPilha[i] == true) {
                    // atualiza o valor de low para o vertice inicial se o vertice adjacente ainda estiver na pilha
                    low[vertice_inicial] = Math.min(low[vertice_inicial], tempo_descoberta[i]);
                }
            }
        }
        // verifica se o vertice inicial  a raiz de um componente fortemente conectado
        if(low[vertice_inicial] == tempo_descoberta[vertice_inicial]){
            while(pilha.peek() != vertice_inicial){
                estaNaPilha[pilha.peek()] = false;
                pilha.pop();
            }
            estaNaPilha[pilha.peek()] = false;
            pilha.pop();
            numero_de_componentes[0]++;
        }
    }

    /**
     * Conta o numero de componentes fortemente conectadas em um grafo dirigido usando o algoritmo de Tarjan.
     * Para grafos nao direcionados, retorna `-1` porque componentes fortemente conectadas nao se aplicam.
     * 
     * @param vertices O numero total de vertices no grafo.
     * @param direcionado_ou_nao_direcionado Uma string que indica se o grafo  direcionado ou nao. Pode ser "direcionado"
     *                                      ou "nao_direcionado".
     * @param matriz_adjacencias A matriz de adjacencias do grafo, onde `matriz_adjacencias[i][j]` 
     *                            representa a existencia de uma aresta entre os vertices `i` e `j`. Um valor diferente
     *                            de `Integer.MAX_VALUE` indica a presenca de uma aresta.
     * 
     * @return Um array de tamanho 1 contendo o numero de componentes fortemente conectadas no grafo. 
     *         Se o grafo nao for direcionado, retorna `-1`.
     * 
     * @see #dfs_tarjan_componentes_fortemente_conexos(int, int, int[][], Integer[], Integer[], Integer[], boolean[], Stack<Integer>, Integer[], Integer[])
     *      metodo auxiliar que realiza a busca em profundidade para identificar os componentes fortemente conectados.
     */
    public static Integer[] contar_componentes_fortemente_conexas(int vertices, String direcionado_ou_nao_direcionado, int[][] matriz_adjacencias) {
        // por mais que queremos retornar um inteiro, teremos que criar um vetor de inteiros com uma posicao
        // porque precisamos passar esse valor por referencia quando chamamos a dfs, e o Java so passa valores
        // de tipos primitivos por copia. Para que funcione como esperado, precisamos passar um vetor, que sempre
        // eh passado por referencia
        Integer[] numero_de_componentes = new Integer[1];
        if (direcionado_ou_nao_direcionado.equals("nao_direcionado")) {
            // nao calculamos componentes fortemente conexas em grafos nao-direcionados
            numero_de_componentes[0] = -1;
            return numero_de_componentes;  
        } else {
            // inicializa os arrays e variaveis necessarias para o algoritmo de Tarjan
            Integer[] tempo = new Integer[1];
            Integer[] visitados = inicializar_vertices_com_zero(vertices);
            Integer[] tempo_descoberta = new Integer[vertices];
            Integer[] low = new Integer[vertices];
            boolean[] estaNaPilha = new boolean[vertices];
            Stack<Integer> pilha = new Stack<>();
            
            tempo[0] = 0;
            numero_de_componentes[0] = 0;

            // inicializa os arrays de tempos de descoberta e low
            for(int i = 0; i < vertices; i++){
                tempo_descoberta[i] = -1;
                low[i] = -1;
                estaNaPilha[i] = false;
            }

            for(int i = 0; i < vertices; i++){
                if(visitados[i] == 0){
                    dfs_tarjan_componentes_fortemente_conexos(i, vertices, matriz_adjacencias, visitados, tempo_descoberta, low, estaNaPilha, pilha, tempo, numero_de_componentes); 
                }
            }
            return numero_de_componentes;
        }
    }
    
    /**
     * Realiza uma busca em profundidade (DFS) para identificar vertices de articulacao em um grafo nao direcionado.
     * Um vertice de articulacao  um vertice que, quando removido, aumenta o numero de componentes conexas do grafo.
     * 
     * @param vertice_inicial O vertice a partir do qual a busca em profundidade deve iniciar.
     * @param vertices O numero total de vertices no grafo.
     * @param matriz_adjacencias A matriz de adjacencias do grafo, onde `matriz_adjacencias[i][j]` 
     *                            representa a existencia de uma aresta entre os vertices `i` e `j`. Um valor diferente
     *                            de `Integer.MAX_VALUE` indica a presenca de uma aresta.
     * @param lista_vertices_articulacao Uma lista que sera preenchida com os vertices de articulacao encontrados durante
     *                                   a busca. Inicialmente, deve estar vazia.
     * @param tempo Um array de tamanho 1 que mantm o tempo global de descoberta dos vertices.
     * @param tempo_descoberta Um array que armazena o tempo em que cada vertice foi descoberto durante a DFS.
     * @param visitados Um array que marca se um vertice foi visitado ou nao. O valor `0` indica nao visitado,
     *                   e `1` indica visitado.
     * @param low Um array que armazena o menor valor de `tempo_descoberta` acessivel a partir de cada vertice.
     * @param pai Um array que armazena o pai de cada vertice na arvore DFS.
     * 
     * @return A lista de vertices de articulacao encontrados durante a busca.
     * 
     * @see #encontrar_vertices_articulacao(int, int[][]) metodo principal que inicia a busca para encontrar todos os
     *      vertices de articulacao no grafo e chama este metodo para explorar cada vertice.
     */
    public static List<Integer> dfs_tarjan_procurando_vertice_de_articulacao(int vertice_inicial, int vertices, int[][]matriz_adjacencias, List<Integer> lista_vertices_articulacao, int[] tempo, Integer[] tempo_descoberta, Integer[] visitados, Integer[] low, Integer[]pai){
        // marca o vertice inicial como visitado
        visitados[vertice_inicial] = 1;
        int filhos = 0;

        // atualiza o tempo de descoberta e o valor de 'low' do vertice inicial
        tempo_descoberta[vertice_inicial] = tempo[0];
        low[vertice_inicial] = tempo[0];
        tempo[0]++;

        for(int i = 0; i < vertices; i++){
            if(matriz_adjacencias[vertice_inicial][i] != Integer.MAX_VALUE){
                if(visitados[i] == 0){
                    filhos++;
                    // define o pai do vertice
                    pai[i] = vertice_inicial;
                    dfs_tarjan_procurando_vertice_de_articulacao(i, vertices, matriz_adjacencias, lista_vertices_articulacao, tempo, tempo_descoberta, visitados, low, pai);
                    // atualiza o valor de 'low' do vertice inicial
                    low[vertice_inicial] =  Math.min(low[vertice_inicial], low[i]);
                    
                    // verifica se "vertice_inicial"  raiz da DFS e se tem pelo menos 2 filhos
                    if(pai[vertice_inicial] == -1 && filhos > 1){ 
                        lista_vertices_articulacao.add(vertice_inicial);
                        System.out.println("ADD NA LISTA " + vertice_inicial);
                    }

                    // verifica se o vertice inicial nao  a raiz da DFS e se algum filho tem um 'low' maior ou igual ao tempo de descoberta do vertice inicial
                    // isso significa que nao ha uma aresta de retorno que conecta o filho ou seus descendentes a um ancestral do vertice inicial
                     if (pai[vertice_inicial] != -1 && low[i] >= tempo_descoberta[vertice_inicial]) { 
                        lista_vertices_articulacao.add(vertice_inicial);
                    }
                }
                else{
                    // se o vertice 'i' ja foi visitado e nao eh o pai do vertice inicial,
                    // atualize o valor de 'low' do vertice inicial. Isso eg feito para
                    // dizer existe uma aresta de retorno no grafo
                    if(i != pai[vertice_inicial]){
                        // atualiza o valor de 'low' do vertice inicial para ser o minimo
                        // entre o valor atual de 'low' e o tempo de descoberta do vertice 'i'.
                        // isso e feito porque a aresta (vertice_inicial, i) pode ser uma
                        // aresta de retorno que conecta o vertice inicial a um ancestral
                        // no grafo, afetando o valor de 'low'
                        low[vertice_inicial] = Math.min(low[vertice_inicial], tempo_descoberta[i]);
                    }
                }
            }
        }

        return lista_vertices_articulacao;
    }

    /**
     * Lista todos os vertices de articulacao em um grafo nao direcionado usando o algoritmo de Tarjan.
     * Um vertice de articulacao  um vertice cuja remocao aumenta o numero de componentes conexas do grafo.
     * Para grafos direcionados, o metodo retorna `-1` indicando que a busca por vertices de articulacao nao  aplicavel.
     * 
     * @param vertices O numero total de vertices no grafo.
     * @param direcionado_ou_nao_direcionado Uma string que indica se o grafo  direcionado ou nao. Pode ser "direcionado"
     *                                      ou "nao_direcionado".
     * @param matriz_adjacencias A matriz de adjacencias do grafo, onde `matriz_adjacencias[i][j]` 
     *                            representa a existencia de uma aresta entre os vertices `i` e `j`. Um valor diferente
     *                            de `Integer.MAX_VALUE` indica a presenca de uma aresta.
     * 
     * @return Uma lista contendo todos os vertices de articulacao encontrados no grafo. 
     *         Se o grafo for direcionado, a lista contera um unico elemento `-1` indicando que a busca nao  aplicavel.
     * 
     * @see #dfs_tarjan_procurando_vertice_de_articulacao(int, int, int[][], List<Integer>, int[], Integer[], Integer[], Integer[], Integer[])
     *      metodo que realiza a busca em profundidade para identificar os vertices de articulacao.
     */
    public static List<Integer> listar_vertices_de_articulacao(int vertices, String direcionado_ou_nao_direcionado, int[][] matriz_adjacencias){
        List<Integer> lista_vertices_articulacao = new ArrayList<Integer>();

        // inicializa variaveis para o algoritmo de Tarjan
        int[] tempo = new int[1];
        tempo[0] = 0;
        Integer[] visitados = inicializar_vertices_com_zero(vertices);
        Integer[] tempo_descoberta = inicializar_vertices_com_zero(vertices);
        Integer[] low = new Integer[vertices];
        Integer[] pai = new Integer[vertices];

        for(int i = 0; i < vertices; i++){
            pai[i] = -1;
            low[i] = Integer.MAX_VALUE;
        }

        if(direcionado_ou_nao_direcionado.equals("nao_direcionado")){
            for(int i = 0; i < vertices; i++){
                if(visitados[i] == 0){
                    // executa a DFS para cada vertice nao visitado
                    lista_vertices_articulacao = dfs_tarjan_procurando_vertice_de_articulacao(i, vertices, matriz_adjacencias, lista_vertices_articulacao, tempo, tempo_descoberta, visitados, low, pai);
                }
            }
        }
        else {
            // nao calculamos para grafos direcionados
            lista_vertices_articulacao.add(-1);
        }
        if(lista_vertices_articulacao.isEmpty()){
            // se a fila ainda estiver vazia, retorna 0
            lista_vertices_articulacao.add(0);
        }
        return lista_vertices_articulacao;
    }

    /**
     * Realiza uma busca em profundidade (DFS) para identificar e contar as arestas ponte em um grafo nao direcionado.
     * Arestas ponte sao arestas cuja remocao aumenta o numero de componentes conexos do grafo.
     * Este metodo  baseado no algoritmo de Tarjan para encontrar arestas ponte.
     * 
     * @param vertice_inicial O vertice a partir do qual a DFS deve comecar.
     * @param vertices O numero total de vertices no grafo.
     * @param matriz_adjacencias A matriz de adjacencias do grafo, onde `matriz_adjacencias[i][j]` representa o peso 
     *                           da aresta entre os vertices `i` e `j`. Um valor de `Integer.MAX_VALUE` indica a 
     *                           ausencia de aresta.
     * @param quantidade_arestas_pontes Um array de tamanho 1 que armazena a quantidade de arestas ponte encontradas.
     *                                  O valor  atualizado pelo metodo.
     * @param tempo Um array de tamanho 1 que armazena o tempo corrente de descoberta dos vertices durante a DFS.
     *              O valor  incrementado pelo metodo.
     * @param tempo_descoberta Um array que armazena o tempo de descoberta de cada vertice.
     * @param visitados Um array que indica se um vertice foi visitado (1 se visitado, 0 caso contrario).
     * @param low Um array que armazena o menor tempo de descoberta alcancado a partir de cada vertice.
     * @param pai Um array que armazena o pai de cada vertice na arvore DFS.
     * 
     * @return O array de tamanho 1 que contm a quantidade de arestas ponte encontradas no grafo.
     * 
     * @see #contar_arestas_pontes(int, String, int[][]) metodo que utiliza este metodo auxiliar para contar
     *      as arestas ponte em um grafo nao direcionado.
     */
    public static int[] dfs_tarjan_listando_arestas_pontes(int vertice_inicial, int vertices, int[][]matriz_adjacencias, int[] quantidade_arestas_pontes, int[] tempo, Integer[] tempo_descoberta, Integer[] visitados, Integer[] low, Integer[]pai){
        // o vertice pela qual a dfs foi chamada eh marcado como visitado
        visitados[vertice_inicial] = 1;

        // atualiza o tempo de descoberta e o low do vertice 
        tempo_descoberta[vertice_inicial] = tempo[0];
        low[vertice_inicial] = tempo[0];

        // incrementa o tempo
        tempo[0]++;

        for(int i = 0; i < vertices; i++){
            if(matriz_adjacencias[vertice_inicial][i] != Integer.MAX_VALUE){
               
                if(visitados[i] == 0){
                    // atualiza o pai do vertice i
                    pai[i] = vertice_inicial;
                    dfs_tarjan_listando_arestas_pontes(i, vertices, matriz_adjacencias, quantidade_arestas_pontes, tempo, tempo_descoberta, visitados, low, pai);
                    // o low do vertice inicial recebe o minimo entre seu low e o low do vertice i
                    low[vertice_inicial] =  Math.min(low[vertice_inicial], low[i]);
                    
                    // verifica se  uma aresta ponte
                    if(low[i] > tempo_descoberta[vertice_inicial]){
                        quantidade_arestas_pontes[0]++;
                    }

                }
                else{
                    if(i != pai[vertice_inicial]){
                        // atualiza o valor de low se o vertice inicial  um ancestral do vertice i
                        low[vertice_inicial] = Math.min(low[vertice_inicial], tempo_descoberta[i]);
                    }
                }
            }
        }
        return quantidade_arestas_pontes;
    }

    /**
     * Conta o numero de arestas ponte em um grafo nao direcionado. 
     * Uma aresta ponte  uma aresta cuja remocao aumenta o numero de componentes conexos do grafo.
     * 
     * Este metodo utiliza o algoritmo de Tarjan para encontrar arestas ponte em grafos nao direcionados.
     * Em grafos direcionados, o metodo retorna -1, pois arestas ponte nao sao aplicaveis a grafos direcionados.
     * 
     * @param vertices O numero total de vertices no grafo.
     * @param direcionado_ou_nao_direcionado Uma string que indica se o grafo  direcionado ou nao-direcionado. 
     *                                      Aceita os valores "direcionado" ou "nao_direcionado".
     * @param matriz_adjacencias A matriz de adjacencias do grafo, onde `matriz_adjacencias[i][j]` representa o peso 
     *                           da aresta entre os vertices `i` e `j`. Um valor de `Integer.MAX_VALUE` indica a 
     *                           ausencia de aresta.
     * 
     * @return O numero de arestas ponte no grafo. Para grafos direcionados, retorna -1.
     * 
     * @see #dfs_tarjan_listando_arestas_pontes(int, int, int[][], int[], int[], Integer[], Integer[], Integer[]) metodo auxiliar 
     *      que realiza a busca em profundidade e identifica arestas ponte.
     */
    public static int contar_arestas_pontes(int vertices, String direcionado_ou_nao_direcionado, int[][] matriz_adjacencias){
        if(direcionado_ou_nao_direcionado.equals("nao_direcionado")){
            // por mais que queremos retornar um inteiro, teremos que criar um vetor de inteiros com uma posicao
            // porque precisamos passar esse valor por referencia quando chamamos a dfs, e o Java so passa valores
            // de tipos primitivos por copia. Para que funcione como esperado, precisamos passar um vetor, que sempre
            // eh passado por referencia
            int[] quantidade_arestas_pontes = new int[1];
            quantidade_arestas_pontes[0] = 0;

            // variavel tempo para armazenar o tempo de descoberta dos vertices 
            int[] tempo = new int[1];
            tempo[0] = 0;
            // inicializa os visitados com 0
            Integer[] visitados = inicializar_vertices_com_zero(vertices);
            // inicializa todos os tempos de descoberta com 0
            Integer[] tempo_descoberta = inicializar_vertices_com_zero(vertices);
            // vetor para armazenar o low dos vertices
            Integer[] low = new Integer[vertices];
            // vetor para armazenar o pai dos vertices
            Integer[] pai = new Integer[vertices];

            // inicializa o pai e o low de cada vertice
            for(int i = 0; i < vertices; i++){
                pai[i] = -1;
                low[i] = Integer.MAX_VALUE;
            }

            // chama a dfs para contar quantas arestas ponte tem o grafo
            // caso o grafo seja desconexo, o loop garante que a dfs sera chamada novamente
            // para calcular as arestas ponte de todas as componentes 
            for(int i = 0; i < vertices; i++){
                if(visitados[i] == 0){
                    dfs_tarjan_listando_arestas_pontes(i, vertices, matriz_adjacencias, quantidade_arestas_pontes, tempo, tempo_descoberta, visitados, low, pai);
                }
            }
            return quantidade_arestas_pontes[0];
        }
        else{
            // nao fazemos para grafos direcionados
            int[] quantidade_arestas_pontes = new int[1];
            quantidade_arestas_pontes[0] = -1;
            return quantidade_arestas_pontes[0];
        }
    }
    
    /**
     * Realiza uma busca em profundidade (DFS) a partir do vertice inicial e constroi uma arvore de profundidade.
     * As arestas visitadas sao adicionadas a lista `arvore` com base na configuracao do grafo (direcionado ou nao-direcionado).
     * 
     * @param vertice_inicial O vertice a partir do qual a busca em profundidade deve comecar.
     * @param vertices O numero total de vertices no grafo.
     * @param direcionado_ou_nao_direcionado Uma string que indica se o grafo  direcionado ou nao-direcionado. 
     *                                      Aceita os valores "direcionado" ou "nao_direcionado".
     * @param matriz_adjacencias A matriz de adjacencias do grafo, onde `matriz_adjacencias[i][j]` representa o peso 
     *                           da aresta entre os vertices `i` e `j`. Um valor de `Integer.MAX_VALUE` indica a 
     *                           ausencia de aresta.
     * @param visitados Um array que indica se um vertice foi visitado (1) ou nao (0).
     * @param arvore Uma lista que sera preenchida com os IDs das arestas que pertencem a arvore de profundidade.
     * @param lista_de_arestas Uma lista de arrays onde cada array contm tres elementos: 
     *                         o ID da aresta, o vertice inicial e o vertice final da aresta. Para grafos nao direcionados, 
     *                         a aresta  representada de forma bidirecional.
     * 
     * @see #gerar_arvore_de_profundidade(int, String, int[][], List<Integer[]>) metodo que utiliza este metodo para construir 
     *      a arvore de profundidade a partir do vertice 0.
     */
    public static void dfs_para_arvore_de_profundidade(int vertice_inicial, int vertices, String direcionado_ou_nao_direcionado, int[][] matriz_adjacencias, Integer[] visitados, List<Integer> arvore, List<Integer[]> lista_de_arestas) {
        // marca o vertice pela qual a dfs foi chamada como visitado
        visitados[vertice_inicial] = 1;
        
        // percorro a matriz
        for (int i = 0; i < vertices; i++) {
            if (matriz_adjacencias[vertice_inicial][i] != Integer.MAX_VALUE && visitados[i] == 0) {
                for (int j = 0; j < lista_de_arestas.size(); j++) {
                    if(direcionado_ou_nao_direcionado.equals("nao_direcionado")){
                        // na lista de arestas, para grafos nao direcionados, armazenamos as arestas em ordem lexicografica (u sempre sera menor que v),
                        // logo, se no grafo o vertice 3 (vertice_inicial) tiver ligacao com o vertice 2 (i), na lista de arestas
                        // isso esta representado como 2-3,  se eu verificar 
                        // na lista de arestas em apenas um sentido, vai dar errado porque o vetor armazena os vertices em
                        // ordem lexicografica, entao essa aresta esta representada como 2-3, sendo o 2 a posicao 1 (u) e o 3 a posicao 2 (v),
                        // porem eu quero verificar de 3 pra 2, sendo 3 o u e 2 o v, por isso precisamos fazer a verificacao
                        // nos dois sentidos 
                        if ((lista_de_arestas.get(j)[1] == vertice_inicial && lista_de_arestas.get(j)[2] == i) || (lista_de_arestas.get(j)[1] == i && lista_de_arestas.get(j)[2] == vertice_inicial)) {
                            // pego o id da aresta 
                            int id_aresta = lista_de_arestas.get(j)[0]; 
                            
                            // adiciono esse id na arvore
                            arvore.add(id_aresta);
                            break;
                        }
                    }
                    else{
                        // em grafos direcionados, nao temos esse problema porque nem sempre
                        // armazenos as arestas em ordem lexicografico, armazenamos conforme o sentido real,
                        // entao se tivermos uma aresta de 3 para 2, ficara aramezado na lista de arestas
                        // de 3 (u) para 2 (v)
                        if (lista_de_arestas.get(j)[1] == vertice_inicial && lista_de_arestas.get(j)[2] == i) {
                            // pego o id da aresta
                            int id_aresta = lista_de_arestas.get(j)[0];

                            // adiciono esse id na arvore
                            arvore.add(id_aresta);
                            break;
                        }
                    }
                }
                dfs_para_arvore_de_profundidade(i, vertices, direcionado_ou_nao_direcionado, matriz_adjacencias, visitados, arvore, lista_de_arestas);
            }  
        }
    }

    /**
     * Gera uma arvore de profundidade (DFS) a partir de um grafo, comecando pelo vertice 0. Em caso de desconexao, 
     * considera apenas a arvore gerada a partir da raiz 0.
     * 
     * @param vertices O numero total de vertices no grafo.
     * @param direcionado_ou_nao_direcionado Uma string que indica se o grafo  direcionado ou nao-direcionado. 
     *                                      Aceita os valores "direcionado" ou "nao_direcionado".
     * @param matriz_adjacencias A matriz de adjacencias do grafo, onde `matriz_adjacencias[i][j]` representa o peso 
     *                           da aresta entre os vertices `i` e `j`. Um valor de `Integer.MAX_VALUE` indica a 
     *                           ausencia de aresta.
     * @param lista_de_arestas Uma lista que sera preenchida com as arestas da arvore de profundidade gerada.
     * 
     * @return Uma lista contendo os vertices na ordem em que foram visitados durante a busca em profundidade.
     * 
     * @see #dfs_para_arvore_de_profundidade(int, int, String, int[][], Integer[], List<Integer>, List<Integer[]>) metodo auxiliar 
     *      que realiza a busca em profundidade e constroi a arvore.
     */
    public static List<Integer> gerar_arvore_de_profundidade(int vertices, String direcionado_ou_nao_direcionado, int[][] matriz_adjacencias, List<Integer[]> lista_de_arestas) {
        // lista para armazenar os id's das arestas
        List<Integer> arvore = new ArrayList<>();
        
        // vetor de visitados
        Integer[] visitados = inicializar_vertices_com_zero(vertices);
        
        // em caso de desconexao, considere apenas a arvore com a raiz 0
        dfs_para_arvore_de_profundidade(0, vertices, direcionado_ou_nao_direcionado, matriz_adjacencias, visitados, arvore, lista_de_arestas);

        return arvore;
    }
    
    /**
     * Gera uma arvore de largura (BFS) a partir de um vertice inicial em um grafo.
     * A arvore de largura  uma representacao das conexões alcancadas por uma busca em largura 
     * iniciada a partir de um vertice especifico. Este metodo utiliza a busca em largura (BFS) 
     * para construir a arvore.
     * 
     * @param vertices O numero total de vertices no grafo.
     * @param direcionado_ou_nao_direcionado Indica se o grafo  direcionado ou nao. Se for "nao_direcionado", 
     *                                       o metodo assume que o grafo  nao direcionado.
     * @param matriz_adjacencias A matriz de adjacencias do grafo, onde `matriz_adjacencias[i][j]` representa o peso 
     *                           da aresta entre os vertices `i` e `j`. Um valor de `Integer.MAX_VALUE` indica a 
     *                           ausencia de aresta.
     * @param lista_de_arestas A lista de arestas do grafo, onde cada aresta  representada por um array contendo 
     *                         o ID da aresta e os vertices conectados por essa aresta. A lista  utilizada para 
     *                         construir a arvore de largura.
     * 
     * @return A lista de IDs das arestas que formam a arvore de largura gerada pela BFS a partir do vertice 0.
     * 
     * @see #bfs_arvore_largura(int, int, String, int[][], List<Integer>, List<Integer[]>) metodo que realiza a BFS 
     *      para gerar a arvore de largura.
     */
    public static List<Integer> bfs_arvore_largura (int vertice_inicial, int vertices, String direcionado_ou_nao_direcionado, int[][] matriz_adjacencias, List<Integer> arvore, List<Integer[]> lista_de_arestas) {
        Queue<Integer> fila = new LinkedList<>();
        Integer[] visitados = inicializar_vertices_com_zero(vertices);
        // marca o vertice como visitado
        visitados[vertice_inicial] = 1;
        // adiciona esse vertice na fila
        fila.add(vertice_inicial);
        // enquanto a fila nao estiver vazia
        while (!fila.isEmpty()) {
            int u = fila.poll();
            for (int v = 0; v < vertices; v++) {
                if (matriz_adjacencias[u][v] != Integer.MAX_VALUE && visitados[v] == 0) { // Existe uma aresta entre u e v
                    fila.add(v);
                    visitados[v] = 1;
                    for (int j = 0; j < lista_de_arestas.size(); j++) {
                        if(direcionado_ou_nao_direcionado.equals("nao_direcionado")){
                            // na lista de arestas, para grafos nao direcionados, armazenamos as arestas em ordem lexicografica (u sempre sera menor que v),
                            // logo, se no grafo o vertice 3 (vertice_inicial) tiver ligacao com o vertice 2 (i), na lista de arestas
                            // isso esta representado como 2-3,  se eu verificar 
                            // na lista de arestas em apenas um sentido, vai dar errado porque o vetor armazena os vertices em
                            // ordem lexicografica, entao essa aresta esta representada como 2-3, sendo o 2 a posicao 1 (u) e o 3 a posicao 2 (v),
                            // porem eu quero verificar de 3 pra 2, sendo 3 o u e 2 o v, por isso precisamos fazer a verificacao
                            // nos dois sentidos 
                            if ((lista_de_arestas.get(j)[1] == u && lista_de_arestas.get(j)[2] == v) || (lista_de_arestas.get(j)[1] == v && lista_de_arestas.get(j)[2] == u)) {
                                // pego o id da aresta 
                                int id_aresta = lista_de_arestas.get(j)[0];

                                // adiciono esse id na arvore
                                arvore.add(id_aresta);
                                break;
                            }
                        }
                        else{
                            // em grafos direcionados, nao temos esse problema porque nem sempre
                            // armazenos as arestas em ordem lexicografico, armazenamos conforme o sentido real,
                            // entao se tivermos uma aresta de 3 para 2, ficara aramezado na lista de arestas
                            // de 3 (u) para 2 (v)
                            if (lista_de_arestas.get(j)[1] == u && lista_de_arestas.get(j)[2] == v) {
                                // pego o id da aresta
                                int id_aresta = lista_de_arestas.get(j)[0];

                                // adiciono esse id na arvore
                                arvore.add(id_aresta);
                                break;
                            }
                        }
                    }
                }
            }
        }
        return arvore;
    }

    /**
     * Gera uma arvore de largura (BFS) a partir de um vertice inicial em um grafo.
     * A arvore de largura  uma representacao das conexões alcancadas por uma busca em largura 
     * iniciada a partir de um vertice especifico. Este metodo utiliza a busca em largura (BFS) 
     * para construir a arvore.
     * 
     * @param vertices O numero total de vertices no grafo.
     * @param direcionado_ou_nao_direcionado Indica se o grafo  direcionado ou nao. Se for "nao_direcionado", 
     *                                       o metodo assume que o grafo  nao direcionado.
     * @param matriz_adjacencias A matriz de adjacencias do grafo, onde `matriz_adjacencias[i][j]` representa o peso 
     *                           da aresta entre os vertices `i` e `j`. Um valor de `Integer.MAX_VALUE` indica a 
     *                           ausencia de aresta.
     * @param lista_de_arestas A lista de arestas do grafo, onde cada aresta  representada por um array contendo 
     *                         o ID da aresta e os vertices conectados por essa aresta. A lista  utilizada para 
     *                         construir a arvore de largura.
     * 
     * @return A lista de IDs das arestas que formam a arvore de largura gerada pela BFS a partir do vertice 0.
     * 
     * @see #bfs_arvore_largura(int, int, String, int[][], List<Integer>, List<Integer[]>) metodo que realiza a BFS 
     *      para gerar a arvore de largura.
     */
    public static List<Integer> gerar_arvore_de_largura(int vertices, String direcionado_ou_nao_direcionado, int[][] matriz_adjacencias, List<Integer[]> lista_de_arestas) {
        List<Integer> arvore = new ArrayList<>();
        
        // em caso de desconexao, considere apenas a arvore com a raiz 0
        bfs_arvore_largura(0, vertices, direcionado_ou_nao_direcionado, matriz_adjacencias, arvore, lista_de_arestas);

        return arvore;
    }

    /**
     * Gera a arvore Geradora Minima (AGM) de um grafo nao direcionado e conexo utilizando o algoritmo de Kruskal,
     * e retorna o valor total da AGM. A arvore Geradora Minima  uma subarvore que conecta todos os vertices do grafo
     * com o menor custo possivel, sem formar ciclos.
     * 
     * @param vertices O numero total de vertices no grafo.
     * @param direcionado_ou_nao_direcionado String que indica se o grafo  direcionado ou nao direcionado.
     *                                       Este metodo opera apenas em grafos nao direcionados.
     * @param matriz_adjacencias A matriz de adjacencias do grafo, onde `matriz_adjacencias[i][j]` representa o peso 
     *                           da aresta entre os vertices `i` e `j`. Um valor de `Integer.MAX_VALUE` indica a 
     *                           ausencia de aresta.
     * @param lista_de_arestas Uma lista de arestas, onde cada aresta  representada por um array de inteiros 
     *                         contendo o identificador da aresta, os vertices conectados por ela e o peso da aresta. 
     *                         Exemplo: `{id, u, v, peso}`.
     * 
     * @return O valor total da arvore Geradora Minima. Se o grafo nao for conexo ou nao for nao direcionado, 
     *         o metodo retorna `-1`.
     * 
     * @see #eh_Conexo(int, String, int[][]) metodo auxiliar que verifica se o grafo  conexo.
     */
    public static int gerar_arvore_geradora_minima(int vertices, String direcionado_ou_nao_direcionado, int[][] matriz_adjacencias, List<Integer[]> lista_de_arestas){
        // verifica se o grafo  nao direcionado e conexo
        if(direcionado_ou_nao_direcionado.equals("nao_direcionado") && eh_Conexo(vertices, direcionado_ou_nao_direcionado, matriz_adjacencias) == 1) {
            // cria uma fila de prioridade (min-heap) onde cada posicao armazena uma aresta,
            // e a fila  organizada pelo peso das arestas (terceira posicao do vetor)
            PriorityQueue<Integer[]> heap = new PriorityQueue<>(Comparator.comparingInt(a -> a[3]));
            int valor_total = 0; // Variavel para armazenar o valor total da AGM

            // adiciona todas as arestas na min-heap
            for(Integer[] aresta : lista_de_arestas) {
                heap.add(aresta);
            }

            int[] conjuntos = new int[vertices];
            // inicializa cada vertice em seu proprio conjunto (para a uniao de conjuntos)
            for (int i = 0; i < vertices; i++) {
                conjuntos[i] = i;
            }
            // contador de arestas incluidas na AGM
            int contador = 0; 
            // enquanto nao tivermos `vertices-1` arestas na AGM
            while(contador < vertices - 1) {
                // remove a aresta de menor peso da heap
                Integer[] aresta = heap.poll();
                int u = aresta[1];
                int v = aresta[2];

                int conjunto_u = conjuntos[u];
                int conjunto_v = conjuntos[v];

                // verifica se os vertices pertencem a diferentes conjuntos
                if(conjunto_u != conjunto_v) {
                    contador++;
                    // adiciona o peso da aresta ao valor total da AGM
                    valor_total += aresta[3];

                    // une os conjuntos de `u` e `v`
                    for (int i = 0; i < vertices; i++) {
                        if (conjuntos[i] == conjunto_v) {
                            conjuntos[i] = conjunto_u;
                        }
                    }
                }
            }

            // retorna o valor total da AGM
            return valor_total;
        } 
        else{
            // retorna -1 se o grafo nao for conexo ou nao direcionado
            return -1; 
        }
    }

    /**
     * Realiza uma busca em profundidade (DFS) para gerar a ordenacao topologica de um grafo direcionado aciclico (DAG).
     * Durante a DFS, os vertices sao processados na ordem de pos-visita, garantindo que cada vertice seja adicionado 
     * a lista de ordenacao topologica somente apos todos os seus sucessores terem sido processados.
     * 
     * @param vertice_inicial O vertice a partir do qual a DFS deve comecar.
     * @param vertices O numero total de vertices no grafo.
     * @param matriz_adjacencias A matriz de adjacencias do grafo, onde `matriz_adjacencias[i][j]` representa o peso 
     *                           da aresta entre os vertices `i` e `j`. Um valor de `Integer.MAX_VALUE` indica a 
     *                           ausencia de aresta.
     * @param armazenados Um array que indica se um vertice foi visitado durante a DFS (1 se visitado, 0 caso contrario).
     * @param ordenacao_topologica A lista que armazena a ordenacao topologica dos vertices.
     *                             Os vertices sao adicionados a lista na ordem inversa da pos-visita.
     * 
     * @see #gerar_ordenacao_topologica(int, String, int[][]) metodo que utiliza este metodo auxiliar para gerar
     *      a ordenacao topologica de um grafo.
     */
    public static void dfs_para_ordenacao_topologica(int vertice_inicial, int vertices, int[][] matriz_adjacencias, Integer[] armazenados, List<Integer> ordenacao_topologica) {
        // marca o vertice como visitado
        armazenados[vertice_inicial] = 1;

        // explora os vertices adjacentes
        for (int i = 0; i < vertices; i++) {
            // verifica se existe uma aresta entre vertice_inicial e i
            if (matriz_adjacencias[vertice_inicial][i] != Integer.MAX_VALUE) {
                // se o vertice ainda nao foi visitado
                if (armazenados[i] == 0) {
                    dfs_para_ordenacao_topologica(i, vertices, matriz_adjacencias, armazenados, ordenacao_topologica);
                }
            }
        }

        // adiciona o vertice a lista de ordenacao topologica sempre no inicio, passando 0 como index no metodo `add`
        ordenacao_topologica.add(0, vertice_inicial);
    }

    /**
     * Gera a ordenacao topologica de um grafo direcionado aciclico (DAG) utilizando busca em profundidade (DFS).
     * A ordenacao topologica  uma linearizacao dos vertices do grafo de tal forma que, para cada aresta u -> v,
     * o vertice u aparece antes do vertice v na ordenacao.
     * 
     * @param vertices O numero total de vertices no grafo.
     * @param direcionado_ou_nao_direcionado String que indica se o grafo  direcionado ou nao direcionado.
     *                                       Este metodo opera apenas em grafos direcionados e aciclicos.
     * @param matriz_adjacencias A matriz de adjacencias do grafo, onde `matriz_adjacencias[i][j]` representa o peso 
     *                           da aresta entre os vertices `i` e `j`. Um valor de `Integer.MAX_VALUE` indica a 
     *                           ausencia de aresta.
     * 
     * @return Uma lista de inteiros que representa a ordenacao topologica dos vertices do grafo.
     *         Se o grafo for nao direcionado ou contiver ciclos, a lista retornada contera apenas o valor `-1`.
     * 
     * @see #possui_ciclo(int, int[][], String) metodo auxiliar que verifica se o grafo possui ciclos.
     * @see #dfs_para_ordenacao_topologica(int, int, int[][], Integer[], List) metodo auxiliar que realiza a DFS
     *      para gerar a ordenacao topologica.
     * @see #inicializar_vertices_com_zero(int) metodo auxiliar que inicializa um array de vertices com valor zero.
     */
    public static List<Integer> gerar_ordenacao_topologica(int vertices, String direcionado_ou_nao_direcionado, int[][] matriz_adjacencias) {
        List<Integer> ordenacao_topologica = new ArrayList<>();
        // verifica se o grafo  nao direcionado ou se possui ciclos
        if (direcionado_ou_nao_direcionado.equals("nao_direcionado") || possui_ciclo(vertices, matriz_adjacencias, direcionado_ou_nao_direcionado) == 1) {
            // retorna -1 se o grafo for nao direcionado ou ciclico
            ordenacao_topologica.add(-1);
        } else {
            // inicializa o array de grau de entrada para todos os vertices
            Integer[] grau_entrada = inicializar_vertices_com_zero(vertices);

            // calcula o grau de entrada de cada vertice
            for (int i = 0; i < vertices; i++) {
                for (int j = 0; j < vertices; j++) {
                    if (matriz_adjacencias[i][j] != Integer.MAX_VALUE) {
                        grau_entrada[j]++;
                    }
                }
            }

            // inicializa os vertices que nao tem grau de entrada
            Integer[] armazenados = inicializar_vertices_com_zero(vertices);

            // realiza DFS para cada vertice com grau de entrada zero que ainda nao foi armazenado
            for (int i = 0; i < vertices; i++) {
                if (armazenados[i] == 0 && grau_entrada[i] == 0) {
                    dfs_para_ordenacao_topologica(i, vertices, matriz_adjacencias, armazenados, ordenacao_topologica);
                }
            }
        }

        // retorna a lista de vertices ordenados topologicamente
        return ordenacao_topologica;
    }

    /**
     * Realiza a operacao de relaxamento em uma aresta de um grafo, atualizando as distâncias mínimas dos vertices e seus pais.
     * O relaxamento e uma operacao fundamental em algoritmos de caminhos mínimos, como o Bellman-Ford.
     * 
     * @param u O vertice de origem da aresta.
     * @param v O vertice de destino da aresta.
     * @param valor Um array que armazena a distância mínima atual de cada vertice a partir da fonte.
     * @param peso O peso da aresta que conecta os vertices `u` e `v`.
     * @param pai Um array que armazena o pai de cada vertice na arvore de caminhos mínimos.
     */
    public static void relaxar(int u, int v, Integer[] valor, int peso, Integer[] pai) {
        // valor_u recebe o valor do caminho minimo na posicao u
        int valor_u = valor[u];

        // valor_v recebe o valor do caminho minimo na posicao v
        int valor_v = valor[v];
        
        // relaxacao da aresta
        if(valor_v > (valor_u + peso)){
            valor[v] = (valor_u + peso);
            pai[v] = u;
        }
    }

    /**
     * Implementa o algoritmo de Bellman-Ford para encontrar o caminho minimo de um grafo nao direcionado a partir do vertice 0.
     * O algoritmo calcula o menor valor de caminho do vertice inicial (0) at todos os outros vertices e detecta possiveis ciclos negativos.
     * Este metodo so funciona para grafos nao direcionados.
     * 
     * @param vertices O numero total de vertices no grafo.
     * @param direcionado_ou_nao_direcionado Especifica se o grafo  direcionado ou nao. Este metodo so lida com grafos nao direcionados.
     * @param arestas O numero total de arestas no grafo.
     * @param matriz_adjacencias A matriz de adjacencias do grafo, onde `matriz_adjacencias[i][j]` representa o peso da aresta entre os vertices `i` e `j`. 
     *                           Um valor de `Integer.MAX_VALUE` indica a ausencia de aresta.
     * 
     * @return O valor do menor caminho do vertice 0 at o vertice de indice `vertices-1`, ou -1 se o grafo for direcionado.
     */
    public static int bellman_ford(int vertices, String direcionado_ou_nao_direcionado, int arestas, int[][] matriz_adjacencias) {
        if(direcionado_ou_nao_direcionado.equals("nao_direcionado")){
            Integer[] pai = new Integer[vertices];
            Integer[] valor = new Integer[vertices];

            valor[0] = 0;
            for (int i = 1; i < vertices; i++) {
                //nao colocamos Integer.Max_Value pois como vamos adicionar um valor a este numero vai dar problema, ja que esse  maior inteiro positivo que o tipo primitivo int suporta
                valor[i] = 10000; // infinito
                pai[i] = -1;
            }

            int iteracao = 0;

            // relaxa todas as arestas repetidamente para encontrar o caminho minimo
            while (iteracao < vertices - 1) {
                for (int i = 0; i < vertices; i++) {
                    for (int j = 0; j < vertices; j++) {
                        if (matriz_adjacencias[i][j] != Integer.MAX_VALUE) {
                            int peso = matriz_adjacencias[i][j];
                            // relaxa a aresta i-j
                            relaxar(i, j, valor, peso, pai);
                            // como o grafo eh nao direcionado, tambm relaxa a aresta j-i
                            relaxar(j, i, valor, peso, pai);
                        }
                    }
                }
                iteracao++;
            }
            
            return valor[vertices-1];
        }
        else{
            // se o grafo for direcionado, o metodo retorna -1
            return -1;
        }
    }

    /**
     * Realiza uma busca em largura (BFS) para encontrar um caminho aumentante no grafo residual.
     * O metodo busca um caminho do vertice inicial at o vertice final, atualizando o array de pais para reconstruir o caminho encontrado.
     * 
     * @param vertice_inicial O vertice inicial do caminho aumentante.
     * @param vertice_final O vertice final do caminho aumentante.
     * @param vertices O numero total de vertices no grafo.
     * @param pai Um array que sera preenchido com o pai de cada vertice no caminho encontrado.
     * @param grafo_residual A matriz de capacidades residuais das arestas no grafo.
     * 
     * @return A capacidade minima encontrada ao longo do caminho aumentante, ou 0 se nao houver caminho aumentante.
     */
    public static int bfs_ford_fulkerson(int vertice_inicial, int vertice_final, int vertices, int[] pai, int[][] grafo_residual){
        // inicializa o array de pais para -1, indicando que nenhum vertice foi visitado ainda.
        for (int i = 0; i < vertices; i++) {
            pai[i] = -1;
        }
        // define o pai do vertice inicial como ele mesmo.
        pai[vertice_inicial] = vertice_inicial;
         // cria uma fila para realizar a BFS, inicializando com o vertice inicial e capacidade infinita.
        Queue<int[]> fila = new LinkedList<>();
        fila.add(new int[]{vertice_inicial, Integer.MAX_VALUE});
        
        // enquanto houver vertices na fila
        while (!fila.isEmpty()) {
            int[] u = fila.poll();
            int vertice = u[0];
            int capacidade = u[1];
            
            for (int i = 0; i < vertices; i++) {
                // verifica se capacidade eh maior que zero e se o vertice adjacente ainda nao foi visitado
                if (grafo_residual[vertice][i] > 0 && pai[i] == -1) {
                    // define o pai do vertice adjacente
                    pai[i] = vertice;
                    int capacidade_minima = Math.min(capacidade, grafo_residual[vertice][i]);
                    if (i == vertice_final) {
                        // retorna a capacidade minima se o vertice final for alcancado
                        return capacidade_minima;
                    }
                    // adiciona o vertice adjacente a fila
                    fila.add(new int[]{i, capacidade_minima});
                }
            }
        }
        // retorna 0 se nao houver caminho aumentante
        return 0;
    }

    /**
     * Implementa o algoritmo de Ford-Fulkerson para encontrar o fluxo maximo em um grafo direcionado.
     * O metodo usa busca em largura (BFS) para encontrar caminhos aumentantes no grafo residual.
     * 
     * @param vertices O numero total de vertices no grafo.
     * @param direcionado_ou_nao_direcionado Indica se o grafo  direcionado ("direcionado") ou nao direcionado ("nao_direcionado").
     * @param arestas O numero total de arestas no grafo.
     * @param matriz_adjacencias A matriz de adjacencias do grafo, onde `matriz_adjacencias[i][j]` representa a capacidade da aresta entre os vertices `i` e `j`. 
     *                           Um valor de `Integer.MAX_VALUE` indica a ausencia de aresta.
     * @param lista_de_arestas Uma lista contendo as arestas do grafo, onde cada aresta  representada por um array de inteiros [indice, u, v, capacidade].
     * 
     * @return O valor do fluxo maximo encontrado no grafo, ou -1 se o grafo for nao direcionado ou contiver arestas com peso menor ou igual a 0.
     */
    public static int ford_fulkerson(int vertices, String direcionado_ou_nao_direcionado, int arestas, int[][] matriz_adjacencias, List<Integer[]> lista_de_arestas) {
        if(direcionado_ou_nao_direcionado.equals("nao_direcionado")) { 
            // nao fazemos para grafos nao-direcionados
            return -1;
        }
        else{  
            // verificacao de validacao das arestas
            for (int i = 0; i < arestas; i++) {
                if (lista_de_arestas.get(i)[3] <= 0) {
                    // grafos com arestas de peso menor ou igual a 0 nao sao validos
                    return -1;
                }
            }

            int[] pai = new int[vertices];
            int[][] grafo_residual = new int[vertices][vertices];
            int capacidade_minima;
            int fluxo_maximo = 0;

            // inicializa grafo residual
            for (int i = 0; i < vertices; i++) {
                for (int j = 0; j < vertices; j++) {
                    if(matriz_adjacencias[i][j] != Integer.MAX_VALUE) {
                        grafo_residual[i][j] = matriz_adjacencias[i][j];
                    }
                    else{
                        // define 0 para arestas inexistentes
                        grafo_residual[i][j] = 0;
                    }
                }
            }

            // enquanto houver um caminho aumentante no grafo residual
            while ((capacidade_minima = bfs_ford_fulkerson(0, vertices - 1, vertices, pai, grafo_residual)) > 0) {
                fluxo_maximo += capacidade_minima;
                int u = vertices - 1;
                // reprocessa o caminho at o vertice inicial
                while (u != 0) {
                    int v = pai[u];
                    // atualiza a capacidade residual no sentido oposto
                    grafo_residual[u][v] += capacidade_minima;
                    // diminui a capacidade residual no sentido do caminho
                    grafo_residual[v][u] -= capacidade_minima;
                    // avanca no caminho
                    u = v;
                }
            }
            return fluxo_maximo;
        }
    } 

    /**
     * Executa uma busca em profundidade (DFS) a partir de um vertice inicial para encontrar todos os vertices alcancaveis.
     * Este metodo e utilizado para calcular o fecho transitivo de um vertice em um grafo direcionado.
     * 
     * @param vertice O vertice a partir do qual a busca em profundidade deve comecar.
     * @param vertices O número total de vertices no grafo.
     * @param matriz_adjacencias A matriz de adjacencias do grafo, onde `matriz_adjacencias[i][j]` representa o peso 
     *                           da aresta entre os vertices `i` e `j`. Um valor de `Integer.MAX_VALUE` indica a 
     *                           ausencia de aresta.
     * @param visitados Um array que indica se um vertice foi visitado (1 se visitado, 0 caso contrario). 
     *                  e atualizado pelo metodo para refletir quais vertices foram alcancados durante a DFS.
     * 
     * @see #fecho_transitivo(int, String, int[][]) Metodo que utiliza este metodo auxiliar para calcular o fecho transitivo
     *      a partir do vertice inicial.
     */
    public static void dfs_fecho_transitivo(int vertice, int vertices, int[][] matriz_adjacencias, Integer[] visitados) {
        // marca o vertice atual como visitado
        visitados[vertice] = 1;

        // itera sobre todos os vertices adjacentes ao vertice atual
        for (int i = 0; i < vertices; i++) {
            // se ha uma aresta do vertice atual para o vertice i e o vertice i ainda nao foi visitado
            if (matriz_adjacencias[vertice][i] != Integer.MAX_VALUE && visitados[i] == 0) {
                // realiza a DFS a partir do vertice i
                dfs_fecho_transitivo(i, vertices, matriz_adjacencias, visitados);
            }
        }
    }

    /**
     * Calcula o fecho transitivo de um grafo direcionado a partir de um vertice inicial. 
     * O fecho transitivo de um vertice eh o conjunto de todos os vertices que podem ser alcancados a partir desse vertice 
     * por meio de uma sequencia de arestas.
     * 
     * @param vertices O número total de vertices no grafo.
     * @param direcionado_ou_nao_direcionado Uma string que indica se o grafo e direcionado ("direcionado") ou nao direcionado ("nao_direcionado").
     * @param matriz_adjacencias A matriz de adjacencias do grafo, onde `matriz_adjacencias[i][j]` representa o peso 
     *                           da aresta entre os vertices `i` e `j`. Um valor de `Integer.MAX_VALUE` indica a 
     *                           ausencia de aresta.
     * 
     * @return Uma lista de inteiros representando o fecho transitivo do vertice inicial (vertice 0). 
     *         Se o grafo nao e direcionado, a lista contera apenas -1.
     * 
     * @see #dfs_fecho_transitivo(int, int, int[][], Integer[]) Metodo auxiliar que realiza a busca em profundidade 
     *      para calcular o fecho transitivo a partir do vertice inicial.
     */
    public static List<Integer> fecho_transitivo(int vertices, String direcionado_ou_nao_direcionado, int[][] matriz_adjacencias){
        List<Integer> fecho = new ArrayList<>();
        
        if(direcionado_ou_nao_direcionado.equals("nao_direcionado")){
            // retorna uma lista contendo -1 para grafos nao direcionados
            fecho.add(-1);
            return fecho;
        } else {
            // inicializa o array de visitados para controlar quais vertices foram alcancados.
            Integer[] visitados = inicializar_vertices_com_zero(vertices);

            // executa uma busca em profundidade a partir do vertice 0 para calcular o fecho transitivo.
            dfs_fecho_transitivo(0, vertices, matriz_adjacencias, visitados);

            // adiciona os vertices que foram alcancados ao fecho transitivo.
            for (int i = 1; i < vertices; i++) {
                if (visitados[i] == 1) {
                    fecho.add(i);
                }
            }
            return fecho;
        }
    }

    /**
     * Realiza uma busca em profundidade (DFS) para listar todos os vertices em uma componente conexa 
     * a partir de um vertice inicial.
     * 
     * @param vertice_inicial O vertice a partir do qual a busca em profundidade deve iniciar.
     * @param vertices O numero total de vertices no grafo.
     * @param matriz_adjacencias A matriz de adjacencias do grafo, onde `matriz_adjacencias[i][j]` 
     *                            representa o peso da aresta entre os vertices `i` e `j`. Um valor de 
     *                            `Integer.MAX_VALUE` indica a ausencia de aresta.
     * @param visitados Array que marca os vertices como visitados ou nao. Um valor de `1` indica que 
     *                  o vertice foi visitado e `0` indica que ainda nao foi visitado.
     * @param componente_conexa Lista que armazenara os vertices da componente conexa a partir do 
     *                           vertice inicial.
     * 
     * @note
     * - O metodo inicializa o vertice inicial como visitado e o adiciona a lista da componente conexa.
     * - Em seguida, percorre todos os vertices adjacentes nao visitados, realizando recursivamente 
     *   a busca em profundidade para encontrar e listar todos os vertices da componente conexa.
     * 
     * @see #componentes_conexas(int, int[][]) Para o metodo que utiliza esta funcao para listar todas 
     *      as componentes conexas em um grafo.
     */
    public static void dfs_para_listar_componentes_conexas_EXTRA(int vertice_inicial, int vertices, int[][] matriz_adjacencias, Integer[] visitados, List<Integer> componente_conexa) {
        visitados[vertice_inicial] = 1;
        componente_conexa.add(vertice_inicial);
        for (int i = 0; i < vertices; i++) {
            if (matriz_adjacencias[vertice_inicial][i] != Integer.MAX_VALUE && visitados[i] == 0) {
                dfs_para_listar_componentes_conexas_EXTRA(i, vertices, matriz_adjacencias, visitados, componente_conexa);
            }
        }
    }

    /**
     * Lista todas as componentes conexas em um grafo nao direcionado. 
     * Para cada componente conexa encontrada, o metodo imprime os vertices que pertencem a ela.
     * Para grafos direcionados, o metodo imprime `-1` indicando que a busca por componentes conexas nao  aplicavel.
     * 
     * @param vertices O numero total de vertices no grafo.
     * @param direcionado_ou_nao_direcionado Uma string que indica se o grafo  direcionado ou nao. Pode ser "direcionado"
     *                                      ou "nao_direcionado".
     * @param matriz_adjacencias A matriz de adjacencias do grafo, onde `matriz_adjacencias[i][j]` 
     *                            representa a existencia de uma aresta entre os vertices `i` e `j`. Um valor diferente
     *                            de `Integer.MAX_VALUE` indica a presenca de uma aresta.
     * 
     * @see #dfs_para_listar_componentes_conexas_EXTRA(int, int, int[][], Integer[], List<Integer>)
     *      metodo auxiliar que realiza uma busca em profundidade (DFS) para listar os vertices de uma componente conexa.
     */
    public static void listar_componentes_conexas_EXTRA(int vertices, String direcionado_ou_nao_direcionado, int[][] matriz_adjacencias) {
        Integer[] visitados = inicializar_vertices_com_zero(vertices);

        if (direcionado_ou_nao_direcionado.equals("nao_direcionado")) {
            for(int i = 0; i < vertices; i++) {
                if(visitados[i] == 0) {
                    // cria uma lista para armazenar os vertices da componente conexa
                    List<Integer> componente_conexa = new ArrayList<>();
                    // realiza a busca em profundidade para listar os vertices da componente conexa
                    dfs_para_listar_componentes_conexas_EXTRA(i, vertices, matriz_adjacencias, visitados, componente_conexa);
                    // imprime a componente conexa encontrada
                    System.out.print("Componente(s) conexa(s): ");
                    for (int componente : componente_conexa) {
                        System.out.print(componente + " ");
                    }
                    System.out.println();
                }
            }
        }
        else{
            // para grafos direcionados, imprime -1 indicando que a busca nao  aplicavel
            List<Integer> grafo_orientado = new ArrayList<>();
            grafo_orientado.add(-1);
                for (int valor : grafo_orientado) {
                    System.out.print(valor);
                }
        }
    }

    /**
     * Implementa o algoritmo de Tarjan para identificar e listar componentes fortemente conexas em um grafo direcionado.
     * 
     * @param vertice_inicial O vertice inicial para a busca em profundidade.
     * @param vertices O numero total de vertices no grafo.
     * @param matriz_adjacencias A matriz de adjacencias do grafo, onde `matriz_adjacencias[i][j]` representa o peso 
     *                            da aresta entre os vertices `i` e `j`. Um valor de `Integer.MAX_VALUE` indica a ausencia de aresta.
     * @param visitados Array que marca os vertices visitados durante a busca. `0` indica nao visitado, `1` indica visitado.
     * @param tempo_descoberta Array que armazena o tempo de descoberta de cada vertice durante a DFS.
     * @param low Array que armazena o menor tempo de descoberta acessivel de cada vertice.
     * @param estaNaPilha Array que indica se um vertice esta na pilha de DFS.
     * @param pilha Pilha utilizada para rastrear os vertices durante a DFS.
     * @param tempo Contador global de tempo para a descoberta dos vertices.
     * @param numero_de_componentes Array que contm o numero de componentes fortemente conexas encontradas.
     * 
     * @see #listar_componentes_fortemente_conexas_EXTRA(int, String, int[][])
     *      metodo que chama `dfs_tarjan_componentes_fortemente_conexos_EXTRA` para calcular o numero de componentes fortemente conexas.
     */
    public static void dfs_tarjan_componentes_fortemente_conexos_EXTRA(int vertice_inicial, int vertices, int[][] matriz_adjacencias, Integer[] visitados, Integer[] tempo_descoberta, Integer[] low, boolean[] estaNaPilha, Stack<Integer> pilha, Integer[] tempo, Integer[] numero_de_componentes){
        visitados[vertice_inicial] = 1;
        tempo_descoberta[vertice_inicial] = tempo[0];
        low[vertice_inicial] = tempo[0];
        tempo[0]++;
        pilha.push(vertice_inicial);
        estaNaPilha[vertice_inicial] = true;
        // explora os vertices adjacentes
        for(int i = 0; i < vertices; i++){
            if(matriz_adjacencias[vertice_inicial][i] != Integer.MAX_VALUE){
                if(visitados[i] == 0){
                    // chama DFS recursivamente para vertices nao visitados
                    dfs_tarjan_componentes_fortemente_conexos_EXTRA(i, vertices, matriz_adjacencias, visitados, tempo_descoberta, low, estaNaPilha, pilha, tempo, numero_de_componentes);
                    low[vertice_inicial] = Math.min(low[vertice_inicial], low[i]);
                }
                else if (estaNaPilha[i] == true) {
                    // atualiza o valor de low se o vertice esta na pilha
                    low[vertice_inicial] = Math.min(low[vertice_inicial], tempo_descoberta[i]);
                }
            }
        }
        // verifica se o vertice atual  o lider de uma componente fortemente conexa
        if(low[vertice_inicial] == tempo_descoberta[vertice_inicial]){
            System.out.println("Componetes fortemente conexas: ");
            while(pilha.peek() != vertice_inicial){
                System.out.print(pilha.peek() + " ");
                estaNaPilha[pilha.peek()] = false;
                pilha.pop();
            }
            System.out.print(pilha.peek());
            System.out.println();
            estaNaPilha[pilha.peek()] = false;
            pilha.pop();
            numero_de_componentes[0]++;
        }
    }

    /**
     * Lista o numero de componentes fortemente conexas em um grafo direcionado.
     * 
     * 
     * @param vertices O numero total de vertices no grafo.
     * @param direcionado_ou_nao_direcionado Indica se o grafo  direcionado ou nao. 
     *                                       Aceita valores "direcionado" e "nao_direcionado".
     * @param matriz_adjacencias A matriz de adjacencias do grafo, onde `matriz_adjacencias[i][j]` 
     *                            representa o peso da aresta entre os vertices `i` e `j`. Um valor de 
     *                            `Integer.MAX_VALUE` indica a ausencia de aresta.
     * 
     * @return Um array de tamanho 1 contendo o numero de componentes fortemente conexas no grafo. 
     *         Se o grafo nao for direcionado, o valor retornado  `-1`.
     * 
     * @see #dfs_tarjan_componentes_fortemente_conexos_EXTRA(int, int, int[][], Integer[], Integer[], Integer[], boolean[], Stack, Integer[], Integer[]) 
     *      metodo auxiliar que implementa o algoritmo de Tarjan para identificar componentes fortemente conexas.
     */
    public static Integer[] listar_componentes_fortemente_conexas_EXTRA(int vertices, String direcionado_ou_nao_direcionado, int[][] matriz_adjacencias) {
        Integer[] numero_de_componentes = new Integer[1];
        if (direcionado_ou_nao_direcionado.equals("nao_direcionado")) {
            // para grafos nao direcionados, o conceito de componentes fortemente conexas nao se aplica.
            numero_de_componentes[0] = -1;
            return numero_de_componentes; // nao calculamos componentes fortemente conexas em grafos nao direcionados 
        } else {
            // inicializa variaveis para o algoritmo de Tarjan
            Integer[] tempo = new Integer[1];
            Integer[] visitados = inicializar_vertices_com_zero(vertices);
            Integer[] tempo_descoberta = new Integer[vertices];
            Integer[] low = new Integer[vertices];
            boolean[] estaNaPilha = new boolean[vertices];
            Stack<Integer> pilha = new Stack<>();
            
            tempo[0] = 0;
            numero_de_componentes[0] = 0;
            for(int i = 0; i < vertices; i++){
                tempo_descoberta[i] = -1;
                low[i] = -1;
                estaNaPilha[i] = false;
            }
            // executa a DFS para cada vertice nao visitado
            for(int i = 0; i < vertices; i++){
                if(visitados[i] == 0){
                    dfs_tarjan_componentes_fortemente_conexos_EXTRA(i, vertices, matriz_adjacencias, visitados, tempo_descoberta, low, estaNaPilha, pilha, tempo, numero_de_componentes); 
                }
            }
            return numero_de_componentes;
        }
    }

    /**
     * Realiza uma busca em profundidade (DFS) para contar o numero de vertices visitados a partir de um vertice inicial.
     * 
     * @param matriz_auxilar A matriz de adjacencias do grafo onde `matriz_auxilar[i][j]` representa a existencia de uma
     *                       aresta entre os vertices `i` e `j`. Um valor diferente de `Integer.MAX_VALUE` indica a presenca
     *                       de uma aresta.
     * @param vertice_inicial O vertice a partir do qual a busca em profundidade deve iniciar.
     * @param vertices O numero total de vertices no grafo.
     * @param visitados Um array que marca se um vertice foi visitado ou nao. O valor `0` indica nao visitado,
     *                   e `1` indica visitado.
     * @param contador Um array que armazena o numero de vertices visitados durante a busca.  passado por referencia para
     *                  que o contador possa ser atualizado dentro da recursao.
     * 
     * @return O numero total de vertices visitados pela DFS a partir do vertice inicial.
     */
    public static int dfs_contadora_EXTRA(int[][] matriz_auxilar, int vertice_inicial, int vertices, Integer[] visitados, int[] contador) {
        // marca o vertice inicial como visitado
        visitados[vertice_inicial] = 1;
        
        // incrementa o contador de vertices visitados
        contador[0]++;
        
        // percorre todos os vertices adjacentes
        for (int i = 0; i < vertices; i++) {
            // se houver uma aresta e o vertice nao foi visitado
            if (matriz_auxilar[vertice_inicial][i] != Integer.MAX_VALUE && visitados[i] == 0) {
                // chama recursivamente DFS para o vertice adjacente
                dfs_contadora_EXTRA(matriz_auxilar, i, vertices, visitados, contador);
            }
        }
        
        // retorna o numero de vertices visitados
        return contador[0];
    }

    /**
     * Verifica se uma aresta  uma ponte em um grafo nao direcionado. 
     * Uma aresta  considerada uma ponte se a sua remocao aumenta o numero de componentes conexas do grafo.
     * 
     * @param matriz_auxilar A matriz de adjacencias do grafo onde `matriz_auxilar[u][v]` representa a existencia de uma
     *                       aresta entre os vertices `u` e `v`. Um valor diferente de `Integer.MAX_VALUE` indica a presenca
     *                       de uma aresta.
     * @param u O vertice inicial da aresta a ser verificada.
     * @param v O vertice final da aresta a ser verificada.
     * @param vertices O numero total de vertices no grafo.
     * 
     * @return `true` se a aresta `(u, v)`  uma ponte, caso contrario `false`.
     * 
     * @see #dfs_contadora(int[][], int, int, Integer[], int[]) metodo auxiliar que realiza uma busca em profundidade
     *      e conta o numero de vertices visitados a partir de um vertice.
     */
    public static boolean eh_ponte_EXTRA(int[][] matriz_auxilar, int u, int v, int vertices) {
        // inicializa o contador e o array de visitados
        int[] contador = new int[1];
        contador[0] = 0;
        Integer[] visitados = inicializar_vertices_com_zero(vertices);

        // conta o numero de vertices visitados antes de remover a aresta
        int contador_01 = dfs_contadora_EXTRA(matriz_auxilar, u, vertices, visitados, contador);
        
        // armazena os valores das arestas e remove a aresta (u, v)
        int valor_01 = matriz_auxilar[u][v];
        int valor_02 = matriz_auxilar[v][u];
        matriz_auxilar[u][v] = Integer.MAX_VALUE;
        matriz_auxilar[v][u] = Integer.MAX_VALUE;

        // reconta o numero de vertices visitados apos remover a aresta
        contador[0] = 0;
        visitados = inicializar_vertices_com_zero(vertices);
        int contador_02 = dfs_contadora_EXTRA(matriz_auxilar, u, vertices, visitados, contador);

        // restaura os valores das arestas
        matriz_auxilar[u][v] = valor_01;
        matriz_auxilar[v][u] = valor_02;

        // verifica se a remocao da aresta alterou o numero de vertices visitados

        // se o numero de vertices visitados da dfs for diferente nas duas contagens, entao a aresta removida eh uma ponte
        if(contador_01 != contador_02){
            // eh ponte
            return true;
        }

        // nao eh ponte
        return false;
    }

    /**
     * Realiza o percorrimento de uma trilha euleriana em um grafo nao direcionado a partir de um vertice inicial.
     * Utiliza uma abordagem recursiva para explorar as arestas do grafo e construir a trilha euleriana.
     * 
     * @param vertice_inicial O vertice a partir do qual o percorrimento da trilha euleriana deve comecar.
     * @param vertices O numero total de vertices no grafo.
     * @param matriz_auxiliar A matriz auxiliar que representa as arestas do grafo, onde `matriz_auxiliar[i][j]` 
     *                        representa a existencia de uma aresta entre os vertices `i` e `j`. Um valor de 
     *                        `Integer.MAX_VALUE` indica a ausencia de aresta.
     * @param grau_do_vertice Um array que armazena o grau de cada vertice. O grau de um vertice  o numero de arestas conectadas a ele.
     * @param trilha_euleriana Uma lista onde os vertices da trilha euleriana sao armazenados na ordem em que sao visitados.
     * 
     * @return Uma lista de inteiros representando a trilha euleriana encontrada a partir do vertice inicial.
     * 
     * @see #eh_ponte_EXTRA(int[][], int, int, int) metodo auxiliar que verifica se uma aresta  uma ponte.
     */
    public static List<Integer> percorrimento_de_trilha_euleriana_EXTRA(int vertice_inicial, int vertices, int[][] matriz_auxilar, Integer[] grau_do_vertice, List<Integer> trilha_euleriana){
        trilha_euleriana.add(vertice_inicial);
        for(int i = 0; i < vertices; i++){
            // verifica se ha uma aresta entre o vertice inicial e o vertice i, e se a aresta nao  uma ponte,
            // ou se o grau do vertice inicial  1
            if(matriz_auxilar[vertice_inicial][i] != Integer.MAX_VALUE && (!eh_ponte_EXTRA(matriz_auxilar, vertice_inicial, i, vertices) || grau_do_vertice[vertice_inicial] == 1)){
                // remove a aresta da matriz auxiliar
                matriz_auxilar[vertice_inicial][i] = Integer.MAX_VALUE;
                matriz_auxilar[i][vertice_inicial] = Integer.MAX_VALUE;
                // atualiza o grau dos vertices
                grau_do_vertice[vertice_inicial]--;
                grau_do_vertice[i]--;
                // recursivamente percorre a trilha euleriana a partir do vertice i
                percorrimento_de_trilha_euleriana_EXTRA(i, vertices, matriz_auxilar, grau_do_vertice, trilha_euleriana);
            }
        }
        return trilha_euleriana;
    }

    /**
     * Conta o numero de vertices visitados a partir de um vertice inicial usando uma busca em profundidade (DFS) 
     * em um grafo direcionado.
     * 
     * @param matriz_auxiliar A matriz auxiliar que representa as arestas do grafo direcionado, onde `matriz_auxiliar[i][j]`
     *                        representa a existencia de uma aresta do vertice `i` para o vertice `j`. Um valor de 
     *                        `Integer.MAX_VALUE` indica a ausencia de aresta.
     * @param vertice_inicial O vertice a partir do qual a contagem de vertices deve comecar.
     * @param vertices O numero total de vertices no grafo.
     * @param visitados Um array que marca se um vertice foi visitado ou nao. O valor `0` indica nao visitado,
     *                   e `1` indica visitado.
     * @param contador Um array que armazena o numero de vertices visitados durante a DFS. O valor  incrementado
     *                 cada vez que um novo vertice  visitado.
     * 
     * @return O numero total de vertices visitados a partir do vertice inicial.
     * 
     * @see #dfs_contadora(int[][], int, int, Integer[], int[]) metodo auxiliar para grafos nao direcionados.
     */
    public static int dfs_contadora_direcionada_EXTRA(int[][] matriz_auxilar, int vertice_inicial, int vertices, Integer[] visitados, int[] contador){
        visitados[vertice_inicial] = 1;
        for (int i = 0; i < vertices; i++) {
            if (matriz_auxilar[vertice_inicial][i] != Integer.MAX_VALUE && visitados[i] == 0) {
                contador[0]++;
                dfs_contadora_direcionada_EXTRA(matriz_auxilar, i, vertices, visitados, contador);
            }
        }
        return contador[0];
    }

    /**
     * Verifica se uma aresta em um grafo direcionado  uma ponte. Para grafos direcionados, uma ponte  uma aresta cuja remocao 
     * aumenta o numero de componentes fortemente conexas no grafo.
     * 
     * @param matriz_auxiliar A matriz de adjacencias do grafo direcionado, onde `matriz_auxiliar[i][j]` representa a existencia 
     *                        de uma aresta do vertice `i` para o vertice `j`. Um valor de `Integer.MAX_VALUE` indica a ausencia de aresta.
     * @param u O vertice inicial da aresta a ser verificada.
     * @param v O vertice final da aresta a ser verificada.
     * @param vertices O numero total de vertices no grafo.
     * 
     * @return `true` se a aresta entre `u` e `v`  uma ponte, ou `false` caso contrario.
     * 
     * @see #converte_matriz_direcionada_para_nao_direcionada(int, int[][]) metodo auxiliar para converter a matriz direcionada em 
     *      uma matriz nao direcionada.
     * @see #dfs_contadora_direcionada_EXTRA(int[][], int, int, Integer[], int[]) metodo auxiliar para contar vertices visitados em 
     *      um grafo direcionado apos a remocao de uma aresta.
     */
    public static boolean eh_ponte_direcionada_EXTRA(int[][]matriz_auxilar, int u, int v, int vertices){ // u = vertice inicial, v = i do for
        int[] contador = new int[1];
        contador[0] = 0;
        Integer[] visitados = inicializar_vertices_com_zero(vertices);
        int[][] matriz_nao_direcionada = converte_matriz_direcionada_para_nao_direcionada(vertices, matriz_auxilar);
        int contador_01 = dfs_contadora_direcionada_EXTRA(matriz_nao_direcionada, u, vertices, visitados, contador);
        

        matriz_nao_direcionada[u][v] = Integer.MAX_VALUE; // removendo a aresta da matriz nao direcionada
        matriz_nao_direcionada[v][u] = Integer.MAX_VALUE; // removendo a aresta da matriz nao direcionada
        
        contador[0] = 0;
        visitados = inicializar_vertices_com_zero(vertices);
        int contador_02 = dfs_contadora_direcionada_EXTRA(matriz_nao_direcionada, u, vertices, visitados, contador);

        if(contador_01 != contador_02){ // se o numero de vertices visitados da dfs for diferente nas duas contagens, entao a aresta removida eh uma ponte
            return true; // eh ponte
        }

        return false; // nao eh ponte
    }

    /**
     * Descobre e constroi uma trilha euleriana em um grafo direcionado usando um algoritmo recursivo. 
     * Para grafos direcionados, uma trilha euleriana  um caminho que visita todas as arestas do grafo exatamente uma vez.
     * 
     * @param vertice_inicial O vertice a partir do qual a trilha euleriana deve comecar.
     * @param vertices O numero total de vertices no grafo.
     * @param matriz_auxiliar A matriz de adjacencias do grafo direcionado, onde `matriz_auxiliar[i][j]` representa a existencia 
     *                        de uma aresta do vertice `i` para o vertice `j`. Um valor de `Integer.MAX_VALUE` indica a ausencia de aresta.
     * @param grau_entrada Um array onde `grau_entrada[i]` representa o numero de arestas de entrada para o vertice `i`.
     * @param grau_saida Um array onde `grau_saida[i]` representa o numero de arestas de saida do vertice `i`.
     * @param trilha_euleriana A lista que sera preenchida com a trilha euleriana encontrada.
     * 
     * @return A lista contendo os vertices na ordem em que sao visitados durante a construcao da trilha euleriana.
     * 
     * @see #eh_ponte_direcionada_EXTRA(int[][], int, int, int) metodo auxiliar para verificar se uma aresta  uma ponte 
     *      em um grafo direcionado.
     */
    public static List<Integer> percorrimento_de_trilha_euleriana_para_grafos_direcionados_EXTRA(int vertice_inicial, int vertices, int[][] matriz_auxilar, int[] grau_entrada, int[] grau_saida, List<Integer> trilha_euleriana){
        trilha_euleriana.add(vertice_inicial);
        for(int i = 0; i < vertices; i++){
            if(matriz_auxilar[vertice_inicial][i] != Integer.MAX_VALUE && (!eh_ponte_direcionada_EXTRA(matriz_auxilar, vertice_inicial, i, vertices) || (grau_saida[vertice_inicial] == 1 && grau_entrada[vertice_inicial] == 0))){
                matriz_auxilar[vertice_inicial][i] = Integer.MAX_VALUE;
                grau_saida[vertice_inicial]--;
                grau_entrada[i]--;
                percorrimento_de_trilha_euleriana_para_grafos_direcionados_EXTRA(i, vertices, matriz_auxilar, grau_entrada, grau_saida, trilha_euleriana);
            }
        }
        return trilha_euleriana;
    }

    /**
     * Encontra uma trilha euleriana em um grafo. Dependendo do tipo do grafo (direcionado ou nao direcionado),
     * o metodo utiliza diferentes abordagens para determinar a existencia da trilha e encontrar o caminho.
     * 
     * @param vertices O numero total de vertices no grafo.
     * @param direcionado_ou_nao_direcionado Uma string que indica se o grafo  direcionado ou nao. Pode ser "direcionado"
     *                                      ou "nao_direcionado".
     * @param matriz_adjacencias A matriz de adjacencias do grafo, onde `matriz_adjacencias[i][j]` 
     *                            representa a existencia de uma aresta entre os vertices `i` e `j`. Um valor diferente
     *                            de `Integer.MAX_VALUE` indica a presenca de uma aresta.
     * 
     * @return Uma lista de inteiros representando a trilha euleriana se ela existir. Se o grafo nao possui uma trilha
     *         euleriana (por exemplo, se o numero de vertices com grau impar  diferente de 2 em grafos nao direcionados,
     *         ou se o grafo nao  pseudossimetrico em grafos direcionados), retorna uma lista contendo `-1`.
     * 
     * @see #percorrimento_de_trilha_euleriana(int, int, int[][], Integer[], List<Integer>)
     *      metodo auxiliar que encontra a trilha euleriana para grafos nao direcionados.
     * @see #percorrimento_de_trilha_euleriana_para_grafos_direcionados(int, int, int[][], int[], int[], List<Integer>)
     *      metodo auxiliar que encontra a trilha euleriana para grafos direcionados.
     */
    public static List<Integer> trilha_euleriana_EXTRA(int vertices, String direcionado_ou_nao_direcionado, int[][] matriz_adjacencias){
        List<Integer> trilha_euleriana = new ArrayList<>();

        // matriz auxiliar que sera manipulada no metodo percorrimento_de_trilha_euleriana
        int[][] matriz_auxiliar = new int[vertices][vertices];
        for(int i = 0; i < vertices; i++){
            for(int j = 0; j < vertices; j++){
                matriz_auxiliar[i][j] = matriz_adjacencias[i][j];
            }
        }

        if(direcionado_ou_nao_direcionado.equals("nao_direcionado")){
            // array para armazenar o grau dos vertices 
            Integer[] grau_do_vertice = inicializar_vertices_com_zero(vertices);

            for(int i = 0; i < vertices; i++){
                for(int j = 0; j < vertices; j++){
                    if(matriz_adjacencias[i][j] != Integer.MAX_VALUE){
                        //atualiza o grau do vertice
                        grau_do_vertice[i]++;
                    }
                }
            }
            
            int quantidade_de_vertices_de_grau_impar = 0;
            for(int i = 0; i < vertices; i++){
                if(grau_do_vertice[i] % 2 != 0){
                   //verifica quantidade de vertices com grau impar
                    quantidade_de_vertices_de_grau_impar++;
                }
            }
            
            if(quantidade_de_vertices_de_grau_impar != 2){
                List<Integer> vazio = new ArrayList<Integer>();
                // retorna um ArrayList com -1, o grafo nao possui trilha euleriana, ja que possui mais de dois vertices de grau impar
                vazio.add(-1);
                return vazio;
            }

            int vertice_inicial = 0;
            for(int i = 0; i < vertices; i++){
                if(grau_do_vertice[i] % 2 != 0){
                    vertice_inicial = i;
                    break;
                }
            }

            trilha_euleriana = percorrimento_de_trilha_euleriana_EXTRA(vertice_inicial, vertices, matriz_auxiliar, grau_do_vertice, trilha_euleriana);
        }
        else{
            List<Integer> vazio = new ArrayList<Integer>();
            // retorna um ArrayList com -1
            vazio.add(-1);
            int[] grau_entrada = new int[vertices];
            int[] grau_saida = new int[vertices];
            // inicializo os vetores
            for (int i = 0; i < vertices; i++) {
                grau_entrada[i] = 0;
                grau_saida[i] = 0;
            }

            for (int i = 0; i < vertices; i++) {
                for (int j = 0; j < vertices; j++) {
                    if (matriz_adjacencias[i][j] != Integer.MAX_VALUE) {
                        grau_saida[i]++;
                        grau_entrada[j]++;
                    }
                }
            }

            // verificar quantos sao pseudossimetricos
            // para que tenha uma trilha euleriana em um grafo direcionado, o numero de vertices
            // pseudossimetricos deve ser "vertices - 2", porque os vertices inicial e final nao sao
            // pseudossimetricos
            int quantidade_de_vertices_pseudossimetricos = 0;
            for (int i = 0; i < vertices; i++) {
                if (grau_entrada[i] == grau_saida[i]) {
                    quantidade_de_vertices_pseudossimetricos++;
                    if (quantidade_de_vertices_pseudossimetricos != vertices - 2) {
                        return vazio;
                    }
                }
            }

            int vertice_inicial = 0;
            for (int i = 0; i < vertices; i++) {
                if (grau_saida[i] == grau_entrada[i] + 1) {
                    vertice_inicial = i;
                }
            }
            
            trilha_euleriana = percorrimento_de_trilha_euleriana_para_grafos_direcionados_EXTRA(vertice_inicial, vertices, matriz_auxiliar, grau_entrada, grau_saida, trilha_euleriana);
            
        }
        return trilha_euleriana;
    }

    /**
     * Gera a arvore Geradora Minima (AGM) de um grafo nao direcionado e conexo usando o algoritmo de Kruskal.
     * A arvore Geradora Minima  uma subarvore que conecta todos os vertices do grafo com o menor custo possivel, 
     * sem formar ciclos.
     * 
     * @param vertices O numero total de vertices no grafo.
     * @param direcionado_ou_nao_direcionado String que indica se o grafo  direcionado ou nao direcionado. 
     *                                       O metodo so opera em grafos nao direcionados.
     * @param matriz_adjacencias A matriz de adjacencias do grafo, onde `matriz_adjacencias[i][j]` representa o peso 
     *                           da aresta entre os vertices `i` e `j`. Um valor de `Integer.MAX_VALUE` indica a 
     *                           ausencia de aresta.
     * @param lista_de_arestas Uma lista de arestas, onde cada aresta  representada por um array de inteiros 
     *                         contendo o identificador da aresta, os vertices conectados por ela e o peso da aresta. 
     *                         Exemplo: `{id, u, v, peso}`.
     * 
     * @return Uma lista de inteiros representando os identificadores das arestas que compõem a arvore Geradora Minima. 
     *         Se o grafo nao for conexo ou nao for nao direcionado, a lista retornada contera apenas o valor `-1`.
     * 
     * @see #eh_Conexo(int, String, int[][]) metodo auxiliar que verifica se o grafo  conexo.
     */
    public static List<Integer> gerar_arvore_geradora_minima_EXTRA(int vertices, String direcionado_ou_nao_direcionado, int[][] matriz_adjacencias, List<Integer[]> lista_de_arestas){
        if(direcionado_ou_nao_direcionado.equals("nao_direcionado") && eh_Conexo(vertices, direcionado_ou_nao_direcionado, matriz_adjacencias) == 1){
            // teremos uma fila de prioridade em que cada posicao da fila armazenara um vetor de inteiros, e essa fila de prioridades sera organizada de acordo com a terceira posicao do vetor, que representa o peso das arestas
            PriorityQueue<Integer[]> heap = new PriorityQueue<>(Comparator.comparingInt(a -> a[3])); 
            List<Integer> arvore_geradora_minima = new ArrayList<Integer>();

            for(Integer[] aresta : lista_de_arestas){
                // assim que adiciona, ja ordena a min-heap
                heap.add(aresta);
            }

            int[] conjuntos = new int[vertices];
            // inicializa cada vertice em seu proprio conjunto
            for (int i = 0; i < vertices; i++) {
                conjuntos[i] = i;
            }

            int contador = 0;
            while(contador < vertices - 1){
                // pega e remove o primeiro/menor elemento da heap
                Integer[] aresta = heap.poll();
                int id_aresta = aresta[0];
                int u = aresta[1];
                int v = aresta[2];

                int conjunto_u = conjuntos[u];
                int conjunto_v = conjuntos[v];

                // verifica se os vertices pertencem a diferentes conjuntos
                if (conjunto_u != conjunto_v) {
                    contador++;
                    arvore_geradora_minima.add(id_aresta);

                    // atualiza os conjuntos, unindo u e v
                    for (int i = 0; i < vertices; i++) {
                        if (conjuntos[i] == conjunto_v) {
                            conjuntos[i] = conjunto_u;
                        }
                    }
                }
            }
            
            return arvore_geradora_minima;
        }
        else{
            List<Integer> arvore_geradora_minima = new ArrayList<Integer>();
            arvore_geradora_minima.add(-1);
            return arvore_geradora_minima;
        }
    }
}