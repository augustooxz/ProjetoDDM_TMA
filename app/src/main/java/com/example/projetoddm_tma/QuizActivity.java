package com.example.projetoddm_tma;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class QuizActivity extends AppCompatActivity {
    private TextView textoPergunta;
    private Button opcaoA, opcaoB, opcaoC, opcaoD;
    private Button[] botoesOpcoes;

    private String[] perguntas = {
            "Qual é a ordem da matriz \n A = [ [1, 2], [3, 4] ]?",
            "O determinante da matriz identidade 2x2 é?",
            "Se A é uma matriz 2x3 e B é uma matriz 3x2, qual é a ordem do produto A * B?",
            "Qual operação não é definida para matrizes quadradas?",
            "O que é necessário para que duas matrizes possam ser multiplicadas?",
            "Qual é o resultado da multiplicação de uma matriz por sua inversa?",
            "Qual propriedade NÃO é válida para a multiplicação de matrizes?",
            "O que caracteriza uma matriz diagonal?",
            "O que acontece quando multiplicamos uma matriz por uma matriz nula?",
            "Qual é a soma dos elementos da diagonal principal de uma matriz chamada?",
            "Se uma matriz tem determinante zero, ela é?",
            "Qual dessas matrizes sempre tem determinante 1?",
            "O que define uma matriz simétrica?",
            "Se A é uma matriz quadrada, qual é o significado de A^T?",
            "Uma matriz identidade multiplicada por qualquer matriz resulta em?",
            "O que acontece se multiplicarmos uma matriz por um escalar?",
            "A transposta da transposta de uma matriz A é?",
            "Qual dessas não é uma propriedade da matriz identidade?",
            "Se A * B = B * A para todas as matrizes A e B, qual propriedade seria verdadeira?",
            "A inversa de uma matriz só existe se seu determinante for?"
    };

    private String[][] opcoes = {
            {"1x2", "2x1", "2x2", "3x3"},
            {"0", "1", "2", "Nenhuma das anteriores"},
            {"2x2", "3x3", "2x3", "3x2"},
            {"Adição", "Subtração", "Multiplicação", "Divisão"},
            {"Mesma ordem", "Número de linhas = número de colunas", "Número de colunas da 1ª = número de linhas da 2ª", "Nenhuma das anteriores"},
            {"Matriz nula", "Matriz identidade", "Matriz transposta", "Depende da matriz"},
            {"Associativa", "Comutativa", "Distributiva", "Elemento neutro"},
            {"Todos elementos iguais", "Elementos fora da diagonal principal são zero", "Diagonal principal com zeros", "É sempre invertível"},
            {"Matriz identidade", "Matriz transposta", "Matriz nula", "Depende da matriz"},
            {"Determinante", "Traço", "Norma", "Autovalor"},
            {"Inversa", "Singular", "Diagonal", "Ortogonal"},
            {"Matriz identidade", "Matriz nula", "Matriz simétrica", "Matriz triangular"},
            {"A = A^T", "A * A^T = I", "A^T * A = I", "Det(A) = 1"},
            {"A transposta", "A inversa", "A diagonalizada", "A simétrica"},
            {"Matriz identidade", "A própria matriz", "Uma matriz nula", "Uma matriz transposta"},
            {"Os elementos são elevados ao quadrado", "A matriz mantém suas propriedades", "Os elementos da diagonal principal não mudam", "O determinante da matriz não muda"},
            {"A própria matriz A", "Uma matriz identidade", "Uma matriz diagonal", "Uma matriz nula"},
            {"Determinante 1", "Ordem qualquer", "Sempre invertível", "Sempre singular"},
            {"Comutativa", "Associativa", "Distributiva", "Elemento neutro"},
            {"Diferente de zero", "Positivo", "Negativo", "Maior que 1"}
    };

    private int[] respostasCorretas = {2, 1, 0, 3, 2, 1, 1, 1, 2, 1, 1, 0, 0, 0, 1, 1, 0, 3, 0, 0};

    private List<Integer> ordemPerguntas;
    private int indicePerguntaAtual = 0;
    private int pontuacao = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        inicializarOrdemPerguntas();

        textoPergunta = findViewById(R.id.question_text);
        opcaoA = findViewById(R.id.option_1);
        opcaoB = findViewById(R.id.option_2);
        opcaoC = findViewById(R.id.option_3);
        opcaoD = findViewById(R.id.option_4);

        botoesOpcoes = new Button[]{opcaoA, opcaoB, opcaoC, opcaoD};

        carregarPergunta();

        View.OnClickListener listenerResposta = v -> {
            desativarBotoes();
            verificarResposta((Button) v);
        };

        opcaoA.setOnClickListener(listenerResposta);
        opcaoB.setOnClickListener(listenerResposta);
        opcaoC.setOnClickListener(listenerResposta);
        opcaoD.setOnClickListener(listenerResposta);
    }

    private void inicializarOrdemPerguntas() {
        ordemPerguntas = new ArrayList<>();
        for (int i = 0; i < perguntas.length; i++) {
            ordemPerguntas.add(i);
        }
        Collections.shuffle(ordemPerguntas);
    }

    private void carregarPergunta() {
        if (indicePerguntaAtual < perguntas.length) {
            int numeroPergunta = ordemPerguntas.get(indicePerguntaAtual);
            textoPergunta.setText(perguntas[numeroPergunta]);

            opcaoA.setText(opcoes[numeroPergunta][0]);
            opcaoB.setText(opcoes[numeroPergunta][1]);
            opcaoC.setText(opcoes[numeroPergunta][2]);
            opcaoD.setText(opcoes[numeroPergunta][3]);

            resetarCorBotoes();
            ativarBotoes();
        } else {
            textoPergunta.setText("Quiz finalizado! Pontuação: " + pontuacao + "/" + perguntas.length);
            esconderBotoes();
        }
    }

    private void verificarResposta(Button botaoSelecionado) {
        int numeroPergunta = ordemPerguntas.get(indicePerguntaAtual);
        int indiceSelecionado = obterIndiceSelecionado(botaoSelecionado);

        botoesOpcoes[respostasCorretas[numeroPergunta]].setBackgroundColor(Color.GREEN);

        if (indiceSelecionado == respostasCorretas[numeroPergunta]) {
            pontuacao++;
        } else {
            botaoSelecionado.setBackgroundColor(Color.RED);
        }

        botaoSelecionado.postDelayed(() -> {
            indicePerguntaAtual++;
            carregarPergunta();
        }, 1500);
    }

    private int obterIndiceSelecionado(Button botao) {
        for (int i = 0; i < botoesOpcoes.length; i++) {
            if (botoesOpcoes[i] == botao) return i;
        }
        return -1;
    }

    private void resetarCorBotoes() {
        int corPadrao = ContextCompat.getColor(this, R.color.buttonBackgroundColor);
        for (Button btn : botoesOpcoes) {
            btn.setBackgroundColor(corPadrao);
        }
    }

    private void desativarBotoes() {
        for (Button btn : botoesOpcoes) {
            btn.setEnabled(false);
        }
    }

    private void ativarBotoes() {
        for (Button btn : botoesOpcoes) {
            btn.setEnabled(true);
        }
    }

    private void esconderBotoes() {
        for (Button btn : botoesOpcoes) {
            btn.setVisibility(View.GONE);
        }
    }
}
