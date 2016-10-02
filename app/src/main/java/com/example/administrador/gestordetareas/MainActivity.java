package com.example.administrador.gestordetareas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText nombreEditext;
    EditText emailEditext;
    Button guardarButton;
    // se declara
    ListView usuariosListView;

    List<String> contactos = new ArrayList<String>();


    ArrayAdapter<String> adapter;
    /// se inicializa
    ContactoDao contactoDao;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // cast and upcast
        nombreEditext = (EditText) findViewById(R.id.editTextNombre);
        emailEditext = (EditText) findViewById(R.id.editTextEmail);
        guardarButton = (Button) findViewById(R.id.buttonGuardar);

        // iniciar la bbdd

        contactoDao = new ContactoDao(this);
        // recuperar todos los contactos
        List<Contacto> contactosEntidad = contactoDao.seleccionarTodos();

        contactos = contactosToString(contactosEntidad);


        // generar la lista estas tres lineas
        usuariosListView = (ListView)findViewById(R.id.listViewUsuarios);

        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,android.R.id.text1,contactos);

        usuariosListView.setAdapter(adapter);

        // para que el boton fumcione dentro del parentesis se escribe new y la o mayuscula para que estire el oncliklistener
        guardarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // boolean isValid = true;
                String nombre = nombreEditext.getText().toString();
                String email = emailEditext.getText().toString();

                // todo validacion

                if(!validarNombre(nombre)){
                    nombreEditext.setError(getString(R.string.nombre_error));


                }else if (!validarEmail(email)){
                    emailEditext.setError(getString(R.string.nombre_error));

                }else{
                    String mensaje = getString(R.string.welcome_msj)+ " "+nombre+" "+email;
                    // debuelve un contexto
                    Toast.makeText(getApplicationContext(),mensaje,Toast.LENGTH_LONG).show();

                    // para crear
                    Contacto c = new Contacto();
                    c.setNombre(nombre);
                    c.setEmail(email);

                    contactoDao.crear(c);

                    // agregar datos
                    String datos = nombre+" "+email;
                    contactos.add(datos);
                    adapter.notifyDataSetChanged();


                    nombreEditext.setText(null);
                    emailEditext.setText(null);
                }


                // snachBar
                // codigo de validacion de nombre y email

               // if (nombreEditext.getText().length()< 3){
                   // nombreEditext.setError(getString(R.string.escriba_nombre));

                    //isValid = false;
                }

                //if (!Patterns.EMAIL_ADDRESS.matcher(emailEditext.getText()).matches()){
                   // emailEditext.setError(getString(R.string.escriba_email));

                   // isValid = false;
               // }
               // return;

           // }

        });


    }
    private List<String> contactosToString(List<Contacto> contactosEntidad){
        List<String> result = new ArrayList<>();
        for (Contacto c:contactosEntidad){
            result.add(c.getNombre()+"/n"+c.getEmail());
        }
        return result;
    }

    private boolean validarNombre(String nombre){
        return !nombre.equals("");


    }

    private boolean validarEmail(String email){
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    // para codificar en una sola linea la validacion de nombre y email
    //private boolean validarNombre(String nombre, String email){
        //return !nombre.equals("")&& Patterns.EMAIL_ADDRESS.matcher(email).matches();
    //}

}


