package tsi.lpv.samuelwagner.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import tsi.lpv.samuelwagner.persistencia.ConnectionFactory;
import tsi.lpv.samuelwagner.persistencia.FilmeDAO;
import tsi.lpv.samuelwagner.tipo.Filme;
import tsi.lpv.samuelwagner.tratadorevento.TratadorEventoPesquisarFilme;

import javax.swing.KeyStroke;

import java.awt.event.InputEvent;
/**
 * A classe <code>IgFilmoteca</code> � a respons�vel por construir a janela gr�fica principal.
 * @author Wagner Almeida
 * @author Samuel Gon�alves.
 *
 */
public class IgFilmoteca extends JFrame {
	private Color corBase =  new Color(17, 17, 17);
	private Color corFonte = new Color(237,255,40);
	private Color corMenu = new Color(148,151,151);
	private Color corSubMenu = new Color(124,61,139);
	private JTextField pesquisarTextField;
	private static JButton buscarFilmeButton;
	private JPanel exibicaoPanel;
	private JList <String> resultadoJList;
	private JScrollPane resultadoScrollPane;
	private JButton atorButton;
	private JButton autorButton;
	private JButton diretorButton;
	private JButton generoButton;
	private JButton rankingButton;
	
	/**
	 * Construtor da classe <code>IgFilmoteca</code> respons�vel pela interface gr�fica do aplicativo Filmoteca.
	 */
	public IgFilmoteca() {
		//Define o nome do app
		setTitle("Darth Movies");
		
		//Muda o icone da barra de t�tulos da janela. Cria um objeto url e passa o endere�o da imagem.
		URL url = this.getClass().getResource("/tsi/lpv/samuelwagner/imagens/Darth_Vader_Mask_64.png");
	
		//Cria um objeto imagem, Obt�m a refer�ncia da imagem da barra e seta a nova imagem.
		Image imagemTitulo = Toolkit.getDefaultToolkit().getImage(url);
		
		//Define a nova imagem para a janela.
		this.setIconImage(imagemTitulo);
		
		//Muda a cor do painel.
		getContentPane().setBackground(corBase);
		getContentPane().setForeground(corFonte);
		getContentPane().setLayout(null);
		
		//Cria o painel lateral.
		JPanel panel = new JPanel();
		panel.setBackground(corBase);
		panel.setBounds(0, 108, 153, 305);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		//Cria o bot�o de cadastrar filme.
		JButton cadastrarFilmeButton = new JButton("Cadastrar Filme");
		cadastrarFilmeButton.setMnemonic(KeyEvent.VK_C);
		cadastrarFilmeButton.setIcon(new ImageIcon(IgFilmoteca.class.getResource("/tsi/lpv/samuelwagner/imagens/video.png")));
		cadastrarFilmeButton.setBackground(Color.BLACK);
		cadastrarFilmeButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		cadastrarFilmeButton.setForeground(corSubMenu);
		cadastrarFilmeButton.setToolTipText("Cadastrar Filmes");
		cadastrarFilmeButton.setBounds(0, 11, 153, 38);
		panel.add(cadastrarFilmeButton);
		
		//Registra o tratador de eventos do bot�o cadastrar filme.
		cadastrarFilmeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new IgCadastrarFilme(IgFilmoteca.this,true);
			}
		});
		
		//Cria o bot�o de procurar diretor.
		diretorButton = new JButton("Buscar Diretor");
		diretorButton.setMnemonic(KeyEvent.VK_D);
		diretorButton.setIcon(new ImageIcon(IgFilmoteca.class.getResource("/tsi/lpv/samuelwagner/imagens/director_sit.png")));
		diretorButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		diretorButton.setForeground(corSubMenu);
		diretorButton.setBackground(Color.BLACK);
		diretorButton.setBounds(0, 158, 153, 38);
		panel.add(diretorButton);
		
		//Registra o tratador de eventos do bot�o do diretor.
		diretorButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new IgTelaPesquisa("Pesquisar Filmes Diretor", "Nome do Diretor: ", IgFilmoteca.this, IgFilmoteca.this.diretorButton);
			}
		});
		
		//Cria o bot�o de buscar por ator.
		atorButton = new JButton("Buscar Ator");		
		atorButton.setMnemonic(KeyEvent.VK_A);
		atorButton.setIcon(new ImageIcon(IgFilmoteca.class.getResource("/tsi/lpv/samuelwagner/imagens/actordark.png")));
		atorButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		atorButton.setBackground(Color.BLACK);
		atorButton.setForeground(corSubMenu);
		atorButton.setBounds(0, 60, 153, 38);
		panel.add(atorButton);
		
		//Registra o tratador de eventos do artista.
		atorButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new IgTelaPesquisa("Pesquisar Filmes Artista", "Nome do artista: ", IgFilmoteca.this, IgFilmoteca.this.atorButton);
			}
		});
		
		//Cria bot�o de busca por autor.
		autorButton = new JButton("Buscar Autor");
		autorButton.setMnemonic(KeyEvent.VK_B);
		autorButton.setIcon(new ImageIcon(IgFilmoteca.class.getResource("/tsi/lpv/samuelwagner/imagens/author.png")));
		autorButton.setBackground(Color.BLACK);
		autorButton.setForeground(corSubMenu);
		autorButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		autorButton.setBounds(0, 109, 153, 38);
		panel.add(autorButton);
		
		//Registra o tratador de eventos do autor.
		autorButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new IgTelaPesquisa("Pesquisar Filmes Autor", "Nome do autor: ", IgFilmoteca.this, IgFilmoteca.this.autorButton);
			}
		});
		
		//Criar bot�o de busca por gen�ro.
		generoButton = new JButton("Filmes G\u00EAnero");
		generoButton.setMnemonic(KeyEvent.VK_G);
		generoButton.setBounds(0, 207, 153, 38);
		panel.add(generoButton);
		generoButton.setIcon(new ImageIcon(IgFilmoteca.class.getResource("/tsi/lpv/samuelwagner/imagens/Hollywood_sign_24.png")));
		generoButton.setBackground(Color.BLACK);
		generoButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		generoButton.setForeground(corSubMenu);
		generoButton.setToolTipText("Pesquisar por g\u00EAneros");
		
		//Registra o tratador de eventos do bot�o de busca de g�neros de filmes.
		generoButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new IgTelaPesquisa("Pesquisar Filmes pelo G�nero", "G�nero: ", IgFilmoteca.this, IgFilmoteca.this.generoButton);
			}
		});
		
		//Criar bot�o de exibir por filmes melhor classificados.
		rankingButton = new JButton("Ver Preferidos");
		rankingButton.setMnemonic(KeyEvent.VK_V);
		rankingButton.setBounds(0, 256, 153, 38);
		panel.add(rankingButton);
		rankingButton.setIcon(new ImageIcon(IgFilmoteca.class.getResource("/tsi/lpv/samuelwagner/imagens/keditbookmarks.png")));
		rankingButton.setBackground(Color.BLACK);
		rankingButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		rankingButton.setForeground(corSubMenu);
		rankingButton.setToolTipText("Visualizar filmes na ordem de prefer\u00EAncia.");

		//Registra o tratador de eventos do bot�o de exibi��o de classifica��o.
		rankingButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
					exibirClassificacao();		
			}
		});
		
		//Cria o painel pra barra de pesquisa.
		JPanel pesquisarPanel = new JPanel();
		pesquisarPanel.setBackground(corSubMenu);
		pesquisarPanel.setBounds(0, 11, 839, 48);
		getContentPane().add(pesquisarPanel);
		pesquisarPanel.setLayout(null);
		
		//Inst�ncia o objeto respons�vel por conter os dados da pesquisa.
		pesquisarTextField = new JTextField();
		pesquisarTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				if(arg0.getKeyCode() == KeyEvent.VK_ENTER)
					new TratadorEventoPesquisarFilme(IgFilmoteca.this, pesquisarTextField.getText()).pesquisarFilme();
			}
		});
		
		//Registra o tratador de eventos do jtextField relativo ao teclado.
		pesquisarTextField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				pesquisarTextField.setText("");
			}
		});
		
		//Cria o campo de pesquisa de texto.
		pesquisarTextField.setText("Pesquisar Filme");
		pesquisarTextField.setToolTipText("Pesquisar Filme");
		pesquisarTextField.setBounds(573, 11, 121, 26);
		pesquisarPanel.add(pesquisarTextField);
		pesquisarTextField.setColumns(10);
		
		//Cria o bot�o de procura de filme.
		buscarFilmeButton = new JButton("Buscar");
		buscarFilmeButton.setMnemonic(KeyEvent.VK_U);
		buscarFilmeButton.setOpaque(false);
		buscarFilmeButton.setBorder(null);
		buscarFilmeButton.setForeground(corSubMenu);
		buscarFilmeButton.setIcon(new ImageIcon(IgFilmoteca.class.getResource("/tsi/lpv/samuelwagner/imagens/search.png")));
		buscarFilmeButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		buscarFilmeButton.setBounds(708, 9, 121, 30);
		pesquisarPanel.add(buscarFilmeButton);
		
		//Registra o tratador de eventos da pesquisa.
		buscarFilmeButton.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				new TratadorEventoPesquisarFilme(IgFilmoteca.this, pesquisarTextField.getText()).pesquisarFilme();
			}
		});
		
		//Inst�ncia o painel de exibi��o.
		exibicaoPanel = new JPanel();
		exibicaoPanel.setBackground(corBase);
		exibicaoPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Darth Movies", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(255, 255, 255)));
		exibicaoPanel.setBounds(161, 70, 668, 387);
		getContentPane().add(exibicaoPanel);
		exibicaoPanel.setLayout(null);
		
		//Define a tela como n�o redimension�vel.
		setResizable(false);
		
		setLocationByPlatform(true);
		
		//Define o tamanho da janela.
		setSize(845, 517);
		
		//Define a a��o a ser tomada quando o bot�o fechar for apertado.
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				IgFilmoteca.this.fecharAplicativo();
			}
		});
		
		JMenuBar principalMenuBar = new JMenuBar();
		principalMenuBar.setBackground(corMenu);
		setJMenuBar(principalMenuBar);
		
		JMenu arquivoMenu = new JMenu("Arquivo");
		arquivoMenu.setBackground(corBase);
		arquivoMenu.setForeground(Color.WHITE);
		principalMenuBar.add(arquivoMenu);
		
		JMenuItem fecharMenuItem = new JMenuItem("Fechar");
		fecharMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_MASK));
		fecharMenuItem.setIcon(new ImageIcon(IgFilmoteca.class.getResource("/tsi/lpv/samuelwagner/imagens/Theater_ticket_24.png")));
		arquivoMenu.add(fecharMenuItem);
		
		//Registra o tratador de eventos do bot�o fechar no menu.
		fecharMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				IgFilmoteca.this.fecharAplicativo();
			}
		});
		
		JMenu navegarMenu = new JMenu("Navegar");
		principalMenuBar.add(navegarMenu);
		
		//Cria o item de menu cadastrar e registra o seu tratador de eventos.
		JMenuItem cadastrarMenuItem = new JMenuItem("Cadastrar Filme");
		cadastrarMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));
		cadastrarMenuItem.setMnemonic(KeyEvent.VK_C);
		cadastrarMenuItem.setIcon(new ImageIcon(IgFilmoteca.class.getResource("/tsi/lpv/samuelwagner/imagens/video.png")));
		navegarMenu.add(cadastrarMenuItem);
		cadastrarMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new IgCadastrarFilme(IgFilmoteca.this,true);
			}
		});
		
		JMenu procurarMenu = new JMenu("Procurar Filme pelo...");
		procurarMenu.setMnemonic(KeyEvent.VK_R);
		procurarMenu.setIcon(new ImageIcon(IgFilmoteca.class.getResource("/tsi/lpv/samuelwagner/imagens/search.png")));
		navegarMenu.add(procurarMenu);
		
		//Cria o item de menu autor e registra o seu tratador de eventos.
		JMenuItem atorMenuItem = new JMenuItem("Ator");
		atorMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, InputEvent.CTRL_MASK));
		atorMenuItem.setIcon(new ImageIcon(IgFilmoteca.class.getResource("/tsi/lpv/samuelwagner/imagens/actordark.png")));
		procurarMenu.add(atorMenuItem);
		atorMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new IgTelaPesquisa("Pesquisar Filmes Artista", "Nome do artista: ", IgFilmoteca.this, IgFilmoteca.this.atorButton);
			}
		});
		
		//Cria o item de menu autor e registra o seu tratador de eventos.
		JMenuItem autorMenuItem = new JMenuItem("Autor");
		autorMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));
		autorMenuItem.setIcon(new ImageIcon(IgFilmoteca.class.getResource("/tsi/lpv/samuelwagner/imagens/author.png")));
		procurarMenu.add(autorMenuItem);
		autorMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new IgTelaPesquisa("Pesquisar Filmes Autor", "Nome do autor: ", IgFilmoteca.this, IgFilmoteca.this.autorButton);
			}
		});
		
		//Cria o item de menu diretor e registra seu tratador de eventos.
		JMenuItem diretorMenuItem = new JMenuItem("Diretor");
		diretorMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_MASK));
		diretorMenuItem.setIcon(new ImageIcon(IgFilmoteca.class.getResource("/tsi/lpv/samuelwagner/imagens/director_sit.png")));
		procurarMenu.add(diretorMenuItem);
		diretorMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new IgTelaPesquisa("Pesquisar Filmes Diretor", "Nome do Diretor: ", IgFilmoteca.this, IgFilmoteca.this.diretorButton);
			}
		});
		
		//Cria o item de menu g�nero e registra seu tratador de eventos.
		JMenuItem generoMenuItem = new JMenuItem("G\u00EAnero");
		generoMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, InputEvent.CTRL_MASK));
		generoMenuItem.setIcon(new ImageIcon(IgFilmoteca.class.getResource("/tsi/lpv/samuelwagner/imagens/Hollywood_sign_24.png")));
		procurarMenu.add(generoMenuItem);
		generoMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new IgTelaPesquisa("Pesquisar Filmes pelo G�nero", "G�nero: ", IgFilmoteca.this, IgFilmoteca.this.generoButton);
			}
		});
		
		//Cria o item de menu preferidos.
		JMenuItem preferidosMenuItem = new JMenuItem("Visualizar Preferidos");
		preferidosMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_MASK));
		preferidosMenuItem.setIcon(new ImageIcon(IgFilmoteca.class.getResource("/tsi/lpv/samuelwagner/imagens/keditbookmarks.png")));
		navegarMenu.add(preferidosMenuItem);
		
		//Registra o tratador de eventos do preferidosMenuItem.
		preferidosMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
					exibirClassificacao();		
			}
		});
		
		JMenu infoMenu = new JMenu("Info");
		principalMenuBar.add(infoMenu);
		
		//Cria o menu sobre.
		JMenuItem mntmSobre = new JMenuItem("Sobre");
		mntmSobre.setIcon(new ImageIcon(IgFilmoteca.class.getResource("/tsi/lpv/samuelwagner/imagens/Cinema-Batman-Old-icon.png")));
		mntmSobre.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
		infoMenu.add(mntmSobre);
		
		//Cria o menu jPopupMenu.
		JPopupMenu jPopupMenu = new JPopupMenu();
		JMenuItem menuItem = new JMenuItem("Atualiza Filme");
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new IgCadastraAtorAutorDiretor(IgFilmoteca.this);
			}
		});
		
		//Registra o tratador de eventos do mouse.
		getContentPane().addMouseListener(new MouseAdapter() {
		@Override
		public void mouseReleased(MouseEvent e) {
			if(e.getButton() == MouseEvent.BUTTON3)
				jPopupMenu.show(getContentPane(), e.getX(), e.getY());
			}
		});
		
		getContentPane().add(jPopupMenu);
		exibicaoPanel.add(jPopupMenu);
		jPopupMenu.add(menuItem);
		
		//Define a tela como v�sivel.
		setLocationRelativeTo(null);
		setVisible(false);
	}//IgFilmoteca
	
	/**
	 * Exibe os filmes em ordem descrescente.
	 */
	private void exibirClassificacao() {
		//Obt�m os filmes
		List<Filme> filmes = FilmeDAO.pesquisarFilmeCriterio();
		
		//Verifica se existem filmes.
		if(filmes == null) {new IgMensagem(this, "N�o h� filmes cadastrados");}
		else{
			List<String> dadosFilmes = new ArrayList<String>();
			Iterator<Filme> it = filmes.iterator();
			while(it.hasNext()){
				Filme filme = it.next();
				dadosFilmes.add(String.format("T�tulo: %-45s Classifica��o Pessoal: %-30s Classifica��o IMDB: %-30s", filme.getTitulo(),filme.getClassificacaoPessoal(),filme.getClassificacaoIMDB()));
			}
			new IgClassificacaoPessoalFilmes(this, dadosFilmes.toArray(new String[0]));
		}
	}
	
	/**
	 * Fecha a conex�o com o banco de dados e encerra o aplicativo.
	 */
	private void fecharAplicativo(){
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				ConnectionFactory.closeConnection();
				System.exit(0);
			}
			
		});
	}
	
	/**
	 * Obt�m a refer�ncia da caixa de texto de pesquisa.
	 * @return <code>JTextField</code>
	 */
	public JTextField getPesquisarTextField() {
		return pesquisarTextField;
	}

	/**
	 * Obt�m a refer�ncia do painel de exibi��o da tela principal onde ser�o realizadas as opera��es.
	 * @return <code>JPanel</code>
	 */
	public JPanel getExibicaoPanel() {
		return exibicaoPanel;
	}
	
	/**
	 * Obt�m a refer�ncia do bot�o de pesquisar filme da tela principal.
	 * @return <code>JButton</code>
	 */
	public static JButton getBuscarFilmeButton() {
		return buscarFilmeButton;
	}
	
	/**
	 * Obt�m a refer�ncia da lista onde ser�o exibidos os resultados das pesquisas.
	 * @return <code>JList</code>
	 */
	public JList <String> getResultadoJList() {
		return resultadoJList;
	}

	/**
	 * Ob�m a refer�ncia do painel rol�vel onde ser�o exibidos os resultados das pesquisa.
	 * @return <code>JSCrollPane</code>
	 */
	public JScrollPane getResultadoScrollPane() {
		return resultadoScrollPane;
	}

	/**
	 * Obt�m a refer�ncia do bot�o de pesquisar ator da tela principal.
	 * @return <code>JButton</code>
	 */
	public JButton getAtorButton() {
		return atorButton;
	}

	/**
	 * Obt�m a refer�ncia do bot�o de pesquisar autor da tela principal.
	 * @return <code>JButton</code>
	 */
	public JButton getAutorButton() {
		return autorButton;
	}

	/**
	 * Obt�m a refer�ncia do bot�o de pesquisar diretor da tela principal.
	 * @return <code>JButton</code>
	 */
	public JButton getDiretorButton() {
		return diretorButton;
	}

	/**
	 * Obt�m a refer�ncia do bot�o de pesquisar genero da tela principal.
	 * @return <code>JButton</code>
	 */
	public JButton getGeneroButtono() {
		return generoButton;
	}
}//class IgFilmoteca
