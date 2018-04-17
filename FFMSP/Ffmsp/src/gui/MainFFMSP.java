package gui;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ForkJoinPool;

import resource.Iterator;

public class MainFFMSP {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Proyecto final FFMSP.");
		System.out.println("Introdusca la cantidad de iteraciones.");
		int iterations = scanner.nextInt();
		System.out.println("Introdusca el fichero de secuencias.");
		String inFilePath = scanner.next();

		try {
			File inFile = new File(inFilePath);
			scanner = new Scanner(new FileInputStream(inFile));

			char[] alphabet = { 'A', 'C', 'G', 'T' };
			int n = 0;
			int m = 0;
			ArrayList<char[]> baseSequences = new ArrayList<>(n);
			while (scanner.hasNext()) {
				char[] secuences = scanner.nextLine().toCharArray();
				m = secuences.length;
				baseSequences.add(secuences);
				n++;
			}

			scanner.close();

			long time = System.currentTimeMillis();
			System.out.println("Calculando, por favor espere");
			Iterator iterator = new Iterator(iterations, alphabet, m, baseSequences);
			ForkJoinPool pool = new ForkJoinPool();
			pool.invoke(iterator);
			time = System.currentTimeMillis() - time;

			File outFile = new File(inFile.getParent(), "solution_" + inFile.getName());
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outFile));
			bufferedWriter.write(iterator.toString());
			bufferedWriter.newLine();
			bufferedWriter.flush();
			bufferedWriter.close();

			System.out.println("Finalizado");
			System.out.println("Tiempo: " + time + "ms");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
