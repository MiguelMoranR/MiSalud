package aplicacionesmoviles.avanzado.todosalau.misalud.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

import aplicacionesmoviles.avanzado.todosalau.misalud.Producto.Producto;
import aplicacionesmoviles.avanzado.todosalau.misalud.R;

public class ProductoListadapter extends BaseAdapter {
     private List<Producto> productos;
     private LayoutInflater inflater;
    private OnEditClickListener editClickListener;
    private OnDeleteClickListener deleteClickListener;
    public void setOnDeleteClickListener(OnDeleteClickListener listener) {
        this.deleteClickListener=listener;

    }
    public interface OnEditClickListener{
        void onEditClick(int position);
    }
    public interface OnDeleteClickListener{
        void onDeleteClick(int position);
    }
    public void setOnEditClickListener(OnEditClickListener listener){
        this.editClickListener=listener;
    }
    public ProductoListadapter(Context context , List<Producto>productos){
        this.productos=productos;
        this.inflater=LayoutInflater.from(context);

    }


    @Override
    public int getCount() {
        return productos.size();
    }

    @Override
    public Object getItem(int position) {
        return productos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            convertView=inflater.inflate(R.layout.list_producto,parent,false);
            viewHolder =new ViewHolder();
            viewHolder.txtId=convertView.findViewById(R.id.etId);
            viewHolder.txtNombre=convertView.findViewById(R.id.etNombre);
            viewHolder.txtprecio=convertView.findViewById(R.id.etprice);
            viewHolder.btnEditar=convertView.findViewById(R.id.btnEditar);
            viewHolder.btnEliminar=convertView.findViewById(R.id.btnEliminar);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder=(ViewHolder) convertView.getTag();
        }
        Producto producto =productos.get(position);
        viewHolder.txtId.setText(String.valueOf("ID de producto:"+producto.getId()));
        viewHolder.txtNombre.setText("nombre"+producto.getName());
        viewHolder.txtprecio.setText("Precio"+producto.getPrice());
        viewHolder.btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(deleteClickListener!=null){
                    editClickListener.onEditClick(position);

                }
            }
        });
        viewHolder.btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (deleteClickListener != null){
                    deleteClickListener.onDeleteClick(position);
                }
            }
        });
        return convertView;
    }
    static class ViewHolder {
        TextView txtId;
        TextView txtNombre;
        TextView txtprecio;
        Button btnEditar;
        Button btnEliminar;
    }
}
