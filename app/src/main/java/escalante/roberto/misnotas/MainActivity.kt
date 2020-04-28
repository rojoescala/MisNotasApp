package escalante.roberto.misnotas

import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import kotlinx.android.synthetic.main.activity_main.*
import java.io.*

class MainActivity : AppCompatActivity() {

    var notas = ArrayList<Nota>()
    lateinit var adaptador : AdaptadorNotas

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fab.setOnClickListener(){
            var intent = Intent(this, activity_agregar_nota::class.java)
            startActivityForResult(intent, 123)
        }

        //notasdePrueba()
        leerNotas()

        adaptador = AdaptadorNotas(this,notas)
        listview.adapter = adaptador

    }

    fun notasdePrueba(){
        notas.add(Nota("prueba 1","contenido de la nota 1"))
        notas.add(Nota("prueba 2","contenido de la nota 2"))
        notas.add(Nota("prueba 3","contenido de la nota 4"))

    }

    fun leerNotas()
    {
        notas.clear()
        var carpeta = File(ubicacion().absolutePath)

        if(carpeta.exists()){
            var archivos = carpeta .listFiles()
            if(archivos != null){
                for (archivos in archivos)
                {
                    leerArchivo(archivos)
                }
            }
        }
    }

    fun leerArchivo(archivo: File){
        val fis = FileInputStream(archivo)
        val di = DataInputStream(fis)
        val br = BufferedReader(InputStreamReader(di))
        var strLine: String? = br.readLine()
        var myData = ""

        //lee los archivos almacenados en la memoria

        while (strLine!=null){
            myData = myData + strLine
            strLine = br.readLine()
        }

        br.close()
        di.close()
        fis.close()
        //elimina el .txt
        var nombre = archivo.name.substring(0, archivo.name.length-4)
        //crea la nota y la agrega a la lista
        var nota = Nota(nombre,myData)
        notas.add(nota)

    }

    private fun ubicacion(): File{
        val folder = File(Environment.getExternalStorageDirectory(), "notas")
        if(!folder.exists()){
            folder.mkdir()
        }
        return folder
    }



     fun OnActivityResult(requestCode: Int, resultCode: Int, data: Intent){

        super.onActivityResult(requestCode,resultCode,data)

        //Se activa cuando regresa del Agregar Nota activity
        if (requestCode == 123)
        {
            leerNotas()
            adaptador.notifyDataSetChanged()
        }
    }



}


