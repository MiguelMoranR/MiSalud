package aplicacionesmoviles.avanzado.todosalau.misalud;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

import aplicacionesmoviles.avanzado.todosalau.misalud.AccessProduc.ProductAP;
import aplicacionesmoviles.avanzado.todosalau.misalud.Producto.Producto;
import aplicacionesmoviles.avanzado.todosalau.misalud.adapter.ProductoListadapter;

public class HomeActivity extends AppCompatActivity {

    private ProductAP productAP;
    private EditText etlNombre ,etlprice;
    private Button btnAgregar, btnMostrar;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        productAP = new ProductAP(this);
        productAP.abrir();

        etlNombre = findViewById(R.id.etlNombre);
        etlprice = findViewById(R.id.etlprecio);
        btnAgregar = findViewById(R.id.btnAgregar);
        btnMostrar = findViewById(R.id.btnMostrar);
        listView = findViewById(R.id.listView);

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarProducto();
            }
        });
      btnMostrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarProductos();
                btnMostrar.setVisibility(View.GONE);
            }
        });
    }

    private void mostrarProductos() {
           List<Producto>productos=productAP.obtnTodProductos();
           StringBuilder stringBuilder = new StringBuilder();
           for(Producto producto : productos){
               stringBuilder.append("ID: ").append(producto.getId()).append(", Nombre: ").append(producto.getName().toString()).append(", precio: ").append(producto.getPrice()).append("\n");
           }
           ProductoListadapter adapter = new ProductoListadapter(this, productos);
           listView.setAdapter(adapter);

           adapter.setOnEditClickListener(new ProductoListadapter.OnEditClickListener() {
               @Override
               public void onEditClick(int position) {
                   Producto producto= productos.get(position);
                   etlNombre.setText(producto.getName());
                   etlprice.setText(producto.getPrice());
                   int idProducto = Integer.parseInt(producto.getId());
                  btnAgregar.setText("guardar");
                  btnMostrar.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View v) {
                          actualizarProducto(idProducto);
                      }
                  });
               }

           });
           adapter.setOnDeleteClickListener(new ProductoListadapter.OnDeleteClickListener() {
               @Override
               public void onDeleteClick(int position) {
                   Producto producto = productos.get(position);
                   eliminarProducto(Integer.parseInt(producto.getId()));
                   adapter.notifyDataSetChanged();
               }
           });
    }

    private void agregarProducto() {
        String nombre = etlNombre.getText().toString();
        String precio = etlprice.getText().toString();
        if (!nombre.isEmpty()&& !precio.isEmpty()){
            long resultado= productAP.insertarProduct(nombre, precio);
            if(resultado !=1){
                Toast.makeText(HomeActivity.this, "Producto Agregado exitosamente", Toast.LENGTH_SHORT).show();
                etlNombre.setText("");
                etlprice.setText("");
            }else {
                Toast.makeText(HomeActivity.this, "Error al agregar Producto", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(HomeActivity.this, "COMPLETE LOS CAMPOS", Toast.LENGTH_SHORT).show();
        }
        actualizarListaProducto();
    }
    private void actualizarProducto(int idUsuario){
        String nombre = etlNombre.getText().toString();
        String precio = etlprice.getText().toString();
        if (!nombre.isEmpty()&& !precio.isEmpty()){
            Toast.makeText(HomeActivity.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
        }
        actualizarListaProducto();
    }
    private void eliminarProducto(int idUsuario){
        productAP.eliminarProducto(idUsuario);
        Toast.makeText(HomeActivity.this, "Producto eliminado correctamente", Toast.LENGTH_SHORT).show();
        actualizarListaProducto();
    }
    private void actualizarListaProducto(){
        mostrarProductos();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Cierre de la conexión con la base de datos al destruir la actividad
        productAP.cerrar();
    }

}