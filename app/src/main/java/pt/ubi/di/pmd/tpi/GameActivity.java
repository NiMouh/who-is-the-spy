package pt.ubi.di.pmd.tpi;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
    }
    // Começa o jogo.
    // Escolhe um utilizador aleatório
    // Acaba de ser feita a pergunta e resposta, aparece botão 'next'
    // Segue este processo até acabar a ronda
    // No final da ronda todos podem fazer uma votação (maioria vence)
    // Fazer isto enquanto existir espiões ou enquanto tiver 3 ou mais restantes
    // OBS.: Qualquer espião pode fazer a qualquer altura um palpite que está num botão 'palpite'
    // aparece as opções de lugares, Ele escolhe um, caso esteja certo o jogo acaba e os espiões ganham
    // caso contrário ele é expulso.

}
