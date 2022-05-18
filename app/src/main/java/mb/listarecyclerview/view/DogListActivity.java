package mb.listarecyclerview.view;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import mb.listarecyclerview.MainActivity;
import mb.listarecyclerview.R;

import mb.listarecyclerview.databinding.ActivityDogListBinding;
import mb.listarecyclerview.model.DogModel;

public class DogListActivity extends AppCompatActivity {
    //Atributos - Diversos
    private ActivityDogListBinding  binding;                            //Tela Xml
    private Integer                 dataMes, dataDia, dataAno;          //Data Pega
    private Integer                 horaHora, horaMinuto;               //Hora Pega
    private List<DogModel>          listaDogs = new ArrayList<>();      //Lista Conteúdo

    //Atributos - Banco Dados
    private SQLiteDatabase          bd;                                 //Banco Dados Classe Sqlite
    private final String            nomeBanco  = "empresa";             //Banco Dados Nome Banco
    private final String            nomeTabela = "clientes";            //Banco Dados Nome Tabela

    //Método Inicial
    @RequiresApi(api = Build.VERSION_CODES.O)   //Anotação Data Picker
    @Override protected void onCreate(Bundle savedInstanceState){super.onCreate(savedInstanceState); setContentView(R.layout.activity_dog_list);
        //Tela Xml (View Binding e Data Binding)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dog_list);

        //Objetos Visíveis - Inicio
        binding.imagemXml.setVisibility(View.GONE);

        //Calendário - Pegar
        binding.calendarioXml.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
        @Override public void onDateChanged(DatePicker datePicker, int yearX, int monthX, int dayX){
            //Setar Data Fragmentada
            dataDia = dayX;  dataMes = monthX;  dataAno = yearX;

            //Setar Data Completa
            Calendar dataPegaCalendario = Calendar.getInstance();
            dataPegaCalendario.set(dataAno, dataMes, dataDia);
            long dataPegaTimeMillis = dataPegaCalendario.getTimeInMillis();

            //Debugar - Exibir
            SimpleDateFormat formatoDataPega = new SimpleDateFormat("dd/MM/yyyy");
            String dataPega  = formatoDataPega.format(dataPegaTimeMillis);
            Log.d("myLog - DataPega Calendário", dataPega);
        }});

        //Relógio    - Pegar
        binding.relogioXml.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
        @Override public void onTimeChanged(TimePicker timePicker, int horaX, int minutoX) {
            //Setar Hora Fragmentada
            horaHora = horaX;   horaMinuto = minutoX;

            //Debugar - Exibir
            LocalTime horaPega3 = LocalTime.of(horaHora, horaMinuto);
            Log.d("myLog - Hora Pega Relógio", String.valueOf(horaPega3));
        }});

        //Botão - Salvar Dados
        binding.salvarBtnXml.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View view) {
            //Tratamento Campos em Branco
            if (dataDia==null || dataMes==null || dataAno==null || horaHora==null || horaMinuto==null){
                Toast.makeText(DogListActivity.this, "Falta Data/Hora", Toast.LENGTH_SHORT).show();
            }else{
                salvar();
            }
        }});

        //Botão - Buscar Todos
        binding.buscarBtnXml.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View view) {
            buscarTodos();
        }});
    }

    //Método - Conexão BD
    public void conectarBD(){
        if(bd == null) {
            //Banco Dados
            bd = openOrCreateDatabase(nomeBanco, MODE_PRIVATE, null);

            //Tabela
            bd.execSQL(
                    "CREATE TABLE IF NOT EXISTS '" + nomeTabela + "'(" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                            "datcadsql DEFAULT CURRENT_TIMESTAMP NOT NULL," +
                            "datcadsist TIMESTAMP NOT NULL," +
                            "nome VARCHAR NOT NULL," +
                            "idade INT(3)," +
                            "preco DOUBLE(11.2)," +
                            "racagravar BOOLEAN," +
                            "data_dia INT(2)," +
                            "data_mes INT(2)," +
                            "data_ano INT(2)," +
                            "hora_hora INT(2)," +
                            "hora_minuto INT(2)," +
                            "foto BLOB)"
            );
        }
    }

    //Método - Salvar
    public void salvar() {
        //BD - Abrir
        conectarBD();

        //Data Atual Sistema (Data e Hora)
        Calendar dataPegaSistema = Calendar.getInstance();
        long momentoAtualSistema = dataPegaSistema.getTimeInMillis();

        //Formato - DataHora
        SimpleDateFormat formatoDataAtual = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formatoHoraAtual = new SimpleDateFormat("HH:mm:ss:SS");

        //Debug - Exibir
        String dataAtual = formatoDataAtual.format(momentoAtualSistema);
        String horaAtual = formatoHoraAtual.format(momentoAtualSistema);
        Log.d("myLog - Data Atual Sistema", dataAtual + " - " + horaAtual);

        ////////////////////////////////

        //Tratar Imagem (Converter para Array Byte)
        Bitmap imagemBMP = ((BitmapDrawable) getDrawable(R.drawable.pitbull)).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        imagemBMP.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream); //ou JPEG
        byte[] imagemArrayByte = byteArrayOutputStream.toByteArray();                    //Min 100%

        ////////////////////////////////

        //Setar Valores
        ContentValues contentValues = new ContentValues();
        contentValues.put("datcadsist", momentoAtualSistema);
        contentValues.put("nome", "Pitbull");
        contentValues.put("idade", 5);
        contentValues.put("preco", 2500.78);
        contentValues.put("racagravar", true);
        contentValues.put("data_dia", dataDia);
        contentValues.put("data_mes", dataMes + 1);     //somar 1 ao mes, pois vem com menos um    ?!
        contentValues.put("data_ano", dataAno);
        contentValues.put("hora_hora", horaHora);
        contentValues.put("hora_minuto", horaMinuto);
        contentValues.put("foto", imagemArrayByte);

        //Salvar
        bd.insert(nomeTabela, null, contentValues);
        Toast.makeText(DogListActivity.this, "Dados Gravados com Sucesso", Toast.LENGTH_SHORT).show();
        finish();
        startActivity(new Intent(this, MainActivity.class));
    }

    //Método - Buscar Todos
    public void buscarTodos(){
        //BD - Abrir
        conectarBD();

        //Objetos Visíveis
        binding.calendarioXml.setVisibility(View.GONE);
        binding.relogioXml.setVisibility(View.GONE);
        binding.imagemXml.setVisibility(View.VISIBLE);

        //Comando
        String consultaSimp1 = "SELECT * FROM '"+nomeTabela+"'";               //Comando SQL
        Cursor cursor        = bd.rawQuery(consultaSimp1, null);    //Ponteiro
        int    quantReg      = cursor.getCount();                              //Quant Registros

        //Verificar Se Há Conteúdo
        if(quantReg > 0){
            //Posição Colunas
            int idI         = cursor.getColumnIndex("id");
            int dataCadSqlI = cursor.getColumnIndex("datcadsql");
            int dataCadSist = cursor.getColumnIndex("datcadsist");
            int nomeI       = cursor.getColumnIndex("nome");
            int idadeI      = cursor.getColumnIndex("idade");
            int precoI      = cursor.getColumnIndex("preco");
            int racaI       = cursor.getColumnIndex("racagravar");
            int diaI        = cursor.getColumnIndex("data_dia");
            int mesI        = cursor.getColumnIndex("data_mes");
            int anoI        = cursor.getColumnIndex("data_ano");
            int horaI       = cursor.getColumnIndex("hora_hora");
            int minutoI     = cursor.getColumnIndex("hora_minuto");
            int imagemI     = cursor.getColumnIndex("foto");

            //Primeiro Registro
            cursor.moveToFirst();

            //Pegar Dados
            while(cursor != null){
                //Pegar Dados
                Integer idPego          = cursor.getInt(idI);
                Long    dataCadSqlPega  = cursor.getLong(dataCadSqlI);
                Long    dataCadSistPega = cursor.getLong(dataCadSist);
                String  nomePego        = cursor.getString(nomeI);
                Integer idadePega       = cursor.getInt(idadeI);
                Double  precoPego       = cursor.getDouble(precoI);
                Integer racaPega        = cursor.getInt(racaI);   //Envia Boolean e Retorna Integer
                Integer diaPego         = cursor.getInt(diaI);
                Integer mesPego         = cursor.getInt(mesI);
                Integer anoPego         = cursor.getInt(anoI);
                Integer horaPega        = cursor.getInt(horaI);
                Integer minutoPego      = cursor.getInt(minutoI);
                byte[]  fotoPega        = cursor.getBlob(imagemI);

                //Setar  Model
                DogModel dogModel = new DogModel();
                dogModel.id                     = idPego;
                dogModel.dataAtualSql           = dataCadSqlPega;
                dogModel.dataAtualSistema       = dataCadSistPega;
                dogModel.nome                   = nomePego;
                dogModel.idade                  = idadePega;
                dogModel.preco                  = precoPego;
                dogModel.racaRecuperar          = racaPega;     //Envia Boolean e Retorna Integer
                dogModel.dataDia                = diaPego;
                dogModel.dataMes                = mesPego;
                dogModel.dataAno                = anoPego;
                dogModel.horaHora               = horaPega;
                dogModel.horaMinuto             = minutoPego;
                dogModel.imagemSqlite           = fotoPega;

                //Setar Lista
                listaDogs.add(dogModel);

                //Ir Para o Próximo Registro (Se Tiver). Posição Começa no Zero
                if(cursor.getPosition() +1 < cursor.getCount()){
                    cursor.moveToNext();
                }else{ break; }
            }
        }else{
            Log.d("myLog", "Banco Dados Vazio!");
            Toast.makeText(DogListActivity.this, "Banco Dados Vazio!", Toast.LENGTH_SHORT).show();
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }

        ///////////////////////////////////////////////////////////////////////////////////

        //Debugar   Objeto
        Log.d("myLog", listaDogs.toString());

        ///////////////////////////////////////////////////////////////////////////////////

        //Debugar Registro Específico
        if(listaDogs.size() >= 1) {
            //Debugar   Booleano (Grava em Inteiro no SqLite)
            Integer racaInteger = listaDogs.get(0).racaRecuperar;
            Boolean racaConvertida;
            if(racaInteger == 1){
                racaConvertida = Boolean.TRUE;
            }else {racaConvertida = Boolean.FALSE;}
            Log.d("myLog Raça", String.valueOf(racaConvertida));

            //Debugar   Data (TimeStamp - Manual)
            Long dataCadSistema = listaDogs.get(0).dataAtualSistema;
            Timestamp timestampCadSistema = new Timestamp(dataCadSistema);
            Date dataConvertSistema = new Date(timestampCadSistema.getTime());
            SimpleDateFormat dataFormatadaSist = new SimpleDateFormat("dd/MM/yyyy");
            Log.d("myLog DataCadastro TimeStamp Manual", dataFormatadaSist.format(dataConvertSistema));

            //Debugar   Data (TimeStamp - Automático) - Traz 1970                                  ?!
            Long dataCadSQL = listaDogs.get(0).dataAtualSql;
            Date date = new Date(dataCadSQL*1000L);
            SimpleDateFormat dataFormatadaSQL = new SimpleDateFormat("dd/MM/yyyy");
            Log.d("myLog DataCadastro TimeStamp Automático", dataFormatadaSQL.format(date));

            //Debugar   Imagem
            ByteArrayInputStream imgStream = new ByteArrayInputStream(listaDogs.get(0).imagemSqlite);
            Bitmap imageBitmap = BitmapFactory.decodeStream(imgStream);
            binding.imagemXml.setImageBitmap(imageBitmap);
        }
    }

    //Segundo Método (Se Criar Activity) ou Método Inicial (Se Voltar Activity. OnCreate Ñ Executa)
    @Override protected void onPostResume() { super.onPostResume(); }

    //BotãoFísicoVoltar (onBack) e TelaEncerrada (onDestroy)
    @Override public void onBackPressed(){super.onBackPressed(); Log.d("myLog", "Back");}
    @Override protected void onDestroy() {super.onDestroy(); Log.d("myLog", "Destroy"); }
}

