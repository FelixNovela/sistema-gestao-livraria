package DAO;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DAOGenerico<T> {

	protected <T> List<T> lerFicheiro(String caminho) {
		List<T> lista = new ArrayList<>();
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(caminho))) {
			lista = (List<T>) ois.readObject();
		} catch (FileNotFoundException e) {
		} catch (IOException | ClassNotFoundException e) {
			System.out.println("Erro ao ler ficheiro: " + caminho);
		}
		return lista;
	} 

	protected <T> void escreverFicheiro(String caminho, List<T> lista) {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(caminho))) {
			oos.writeObject(lista);
		} catch (IOException e) {
			System.out.println("Erro ao escrever no ficheiro: " + caminho);
		}
	}
}
