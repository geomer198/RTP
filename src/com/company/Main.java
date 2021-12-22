package com.company;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import static com.company.MaxFlowDinic.*;

public class Main {



    static void help() {
        System.out.print(
                "Program start without parameters!\n" +
                "Help!\n" +
                        "Arguments:\n" +
                        "\t1. First argument is file path with graph.\n" +
                        "\t2. Second argument is algorithm number (1,2,3).\n" +
                        "\t\t(1. Ford Fulkerson)\n" +
                        "\t\t(2. Max Flow Preflow)" +
                        "\t\t(3. Max Flow Dinic)\n"
        );
    }


    public static int[][] getGraphFromFile(String pathToFile) throws IOException {
        int N;
        int[][] graph;
        try {
            FileReader fw = new FileReader(pathToFile);Scanner scan = new Scanner(fw);
            N = scan.nextInt();
            graph = new int[N][N];
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    graph[i][j] = scan.nextInt();
                }
            }
            fw.close();
            return graph;
        } catch (FileNotFoundException e){
            System.out.println("File not found!");
            System.exit(0);
        }
        return null;
    }

    public static void getAlgByArgs(String pathToFile, int algorithmNumber) throws IOException {
        int[][] graphResult = getGraphFromFile(pathToFile);
        int len = graphResult.length;

        switch (algorithmNumber) {
            case 1:
                FordFulkerson ff = new FordFulkerson();
                int result = ff.fordFulkerson(graphResult, 0, len-1);
                System.out.println("Max Flow: "+ result);
                break;
            case 2:
                MaxFlowPreflowN3 flow = new MaxFlowPreflowN3();
                flow.init(len);
                for (int i = 0; i < len; i++)
                    for (int j = 0; j < len; j++)
                        if (graphResult[i][j] != 0)
                            flow.addEdge(i, j, graphResult[i][j]);
                System.out.println(flow.maxFlow(0, 2));
                break;
            case 3:
                if (len == 3) {
                    try {
                        List<MaxFlowDinic.Edge>[] graph = createGraph(len);
                        addEdge(graph, graphResult[0][0], graphResult[0][1], graphResult[0][2]);
                        addEdge(graph, graphResult[1][0], graphResult[1][1], graphResult[1][2]);
                        addEdge(graph, graphResult[2][0], graphResult[2][1], graphResult[2][2]);
                        System.out.println(maxFlow(graph, 0, len-1));
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("Invalid input Graph");
                    }

                } else{
                    System.out.println("Algorithm for working with a 3x3 matrix.");
                }
                break;
            default:
                System.out.println("Algorithm with it number is not found!");

        }
    }


    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            help();
        }
        int algNUmber = 0;
        try {
            algNUmber = Integer.parseInt(args[1]);
        }catch (NumberFormatException e){
            System.out.println("Algorithm number is integer!");
            System.exit(0);
        }
        if (algNUmber != 0) {
            getAlgByArgs(args[0], algNUmber);
        }
    }
}