package com.kreatyve;

import javax.swing.JProgressBar;
import java.util.List;
import java.util.ArrayList;
import java.awt.EventQueue;

/**
* Exibe uma JProgressBar contendo a quantidade de memória utilizada e total.
* @author    Cristian Henrique (cristianmsbr@gmail.com)
* @version   1.5
* @since     Segunda atualização
*/

public class MemoryBar extends JProgressBar {
	private Thread t = null;
	private MemoryReader reader = new MemoryReader();
	final int MEMORY_MAX = (int) ((float) Runtime.getRuntime().maxMemory() / 1048576);

	/**
	* O construtor se encarrega de definir o valor máximo da barra de progresso,
	* determina se a barra deve processar uma cadeia de progresso, define uma
	* String nula para a barra e chama o método que inicia o Thread.
	*/
	public MemoryBar() {
		setMaximum(100);
		setStringPainted(true);
		setString("");
		initiate();
	}

	/**
	* Cria um novo Thread, define ele como daemon, para não impedir a JVM de sair enquanto
	* ele estiver em execução, por fim, o Thread é iniciado.
	*/
	public void initiate() {
		t = new Thread(reader, "Memory Bar");
		t.setDaemon(true);
		t.start();
	}

	/**
	* Responsável por capturar a quantidade de mémoria e atualizar a barra de progresso
	* (String e valor da barra).
	*/
	private class MemoryBar implements Runnable {
		/* Captura a quantidade de memória total e livre, depois é realizada a divisão da subtração
		   das mesmas por 1048576.0, para se obter o valor em MB. Em seguida, o método atualizarBarra
		   é chamado para fazer a exibição dos valores. */
		public void run() {
			try {
				while (!Thread.interrupted()) {
					long total = Runtime.getRuntime().totalMemory();
					long free = Runtime.getRuntime().freeMemory();
					double memUsed = (total - livre) / 1048576.0;
					updateBar(memUsed);

					Thread.sleep(1000);		// espera 1 seg para fazer a nova atualização
				}
			} catch (Exception ex) {  }
		}

		/* Define o valor da barra de progresso, seu texto e ToolTip */
		public void updateBar(final double memoryUsed) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					setValue((int) (memoryUsed * 100.0 / MEMORY_MAX));
					setString(String.format("%.2fMB de %dMB", memoriaUsada, MEMORIA_MAXIMA));
					setToolTipText(String.format("Memory: %.2fMB de %dMB", memoryUsed, MEMORY_MAX));
				}
			});
		}
	}
}