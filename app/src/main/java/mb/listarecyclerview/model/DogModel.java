package mb.listarecyclerview.model;
import java.io.Serializable;
import java.sql.Blob;
import java.util.Arrays;

public class DogModel implements Serializable {
    //Atributos - Texto e Números
    public Integer  id;                     //Identificador Registro
    public String   nome;                   //Texto
    public Integer  idade;                  //Inteiro
    public Double   preco;                  //Decimal

    //Atributos - Booleano
    public Boolean  racaGravar;
    public Integer  racaRecuperar;          //Recupera Inteiro (1 = True e 0 = False)

    //Atributos Data
    public Long     dataAtualSql;
    public Long     dataEditarSql;
    public Long     dataAtualSistema;       //Imutável - Data e Hora Cadastro
    public Long     dataEditarSistema;      //Mutável  - Data e Hora Última Atualização
    public Integer  dataMes;
    public Integer  dataDia;
    public Integer  dataAno;
    public Integer  horaHora;
    public Integer  horaMinuto;

    //Atributos Imagem
    public Integer  imagemGeral;            //Imagem - Geral (setImageResource)
    public String   imagemRetrofit;         //Imagem - Consumir API Rest (Json) Via Retrofit
    public byte[]   imagemSqlite;           //Imagem - Bd SqLite

    //Construtor - Vazio (Manual), Api (Json), SemId (Salvar), ComId(Editar), Etc
    public DogModel() { }

    //Override To String    || Quebrar Linha (+ "\n")   || Não Debugar Imagens
    @Override public String toString() {
        return "DogModel{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", idade=" + idade +
                ", preco=" + preco +
                ", racaGravar=" + racaGravar +
                ", racaRecuperar=" + racaRecuperar +
                ", dataAtualSql=" + dataAtualSql +
                ", dataEditarSql=" + dataEditarSql +
                ", dataAtualSistema=" + dataAtualSistema +
                ", dataEditarSistema=" + dataEditarSistema +
                ", dataMes=" + dataMes +
                ", dataDia=" + dataDia +
                ", dataAno=" + dataAno +
                ", horaHora=" + horaHora +
                ", horaMinuto=" + horaMinuto +
                '}';
    }
}

//Observações
//* GetInstance:        Não, Pois Pode Precisar Instanciar Mais de 1 Objeto. Lista Fixa por exemplo;

//Imagens
//- Geral:              Tipo Inteiro (Int/Integer)
//- Sqlite:             Tipo byte[]
//- Retrofit:           Tipo String | Lib Glide | Conv Gson     Conf. Trat. Imagem Tb (Pasta Util)

//Model - Normal
//- Serializable:       Passar Objetos Entre Activities         Mas Parcelable é Mais Otimizado
//- Private:            Convenção Java (acesso restrito)        Necessita GetterSetter
//- Atributo Tipo:      Conforme Tipo do Atributo               Exceto Imagens e Quando API        ***
//- Atributo Lista:     Declarar (List - Mais Abrangente) e     Instanciar (ArrayList);
//- GetInstance:        Tratamento Para Otimizar Memória        Se Já Instanc, Não Instanc de novo
//- Construtor:         Vazio, API, Sem ID, Com ID              Pode Ter Outros Também
//- GetterSetter:       Para Acessar Campos Private             Se Campo Public, Então Não Precisa
//- Over To String:     Para Debugar Objeto/Lista/ItemLista     Senão, Pode Aparecer "End. Memória"

//Model - API
//- SerializedName:     Renomear Atributos. É Opcional          1o (Conf API) e 2o (Conf Desejado)
//- Atributo Tipo:      Conforme Seu Tipo, E Não Tudo String    Alguns Tutoriais Colocam Td String
//- Atributo Objeto:    Tipo Classe                             Tipo Objeto é { }
//- Atributo Array:     Tipo Lista (Conforme Tipo Lista)        Tipo Array  é [ ]
//* Exemplo Array:      @SerializedName("weight")               private List<String> listaPeso;

