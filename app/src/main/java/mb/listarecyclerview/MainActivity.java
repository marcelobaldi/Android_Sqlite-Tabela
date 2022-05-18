//Pacote e Imports
package mb.listarecyclerview;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import mb.listarecyclerview.view.DogListActivity;

//Classe
public class MainActivity extends AppCompatActivity {
    //Atributos
    //...

    //Método Inicial
    @Override protected void onCreate(Bundle savedInstanceState){super.onCreate(savedInstanceState);
        //Tela XMl (Identificar Via Nativo)
        setContentView(R.layout.activity_main);

        //Redirecionamento
        startActivity(new Intent(this, DogListActivity.class));
    }
}

//Observações - Gerais                                                                             ***
//- Manifest:   Falta Permissões    (WiFi, Ligação, Etc)
//- Gradle:     Falta Libs          (Room, SqLite, Firebase, Etc)

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

//Observações - SQL
//- Debugar Código (Estrutura):         Só Mudar Nome BD        Wipe Data Emulador/SGBD DB Browser Demora Mais
//- Variável String no Comando:         '"+nomeTabela+"'        "SELECT * FROM '"+nomeTabela+"'";
//- Quebra Linha:                        "+"                    A IDE Coloca Automático
//* Setar Mês Pego:			            Acrescentar + 1		    Utilizado Objeto DatePicker;

//Observações - Este Projeto
//- Estrutura:      Mal Estruturado     Finalidade Apenas de Sintaxe;
//- Otimização:     Mal Otimizado       Falta Fechar Banco Dados e/ou Cursor Após Sua Manipulação;
//- Tratamentos:    Tratamento Básico   Só Para App Não Quebrar;

//Dúvidas - Urgentes
//- Salvar/Editar:      Padrão Case Sensitive. Minúsculo, Maiúsculo, Primeiras Maiúsculas, Conf Digitado;
//- Buscar:             Ignorar Case Sensitive;

//Dúvidas - Normais
//- Registro. Como Obter a Data de Cadastro Automática do SqLite?		(Esta Trazendo Ano 1970);
//- Registro. Como Fazer a Data da Última Atualização Automática?
//* Não é Urgente, Pois Estou Fazendo Manualmente;

