package adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mobile.megakons.sa.R;
import com.smarteist.autoimageslider.SliderViewAdapter;

public class AdaptadorSliderMenuPrincipal extends SliderViewAdapter<AdaptadorSliderMenuPrincipal.Holder> {

    int[] images;

    public AdaptadorSliderMenuPrincipal(int[] images){
        this.images = images;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_layout_slider_menu_principal, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder viewHolder, int position) {
        viewHolder.imageView_slider_menu_principal.setImageResource(images[position]);
    }

    @Override
    public int getCount() {
        return images.length;
    }

    public class Holder extends SliderViewAdapter.ViewHolder{
        ImageView imageView_slider_menu_principal;

        public Holder(View itemView){
            super(itemView);
            imageView_slider_menu_principal = itemView.findViewById(R.id.imageView_slider_menu_principal);
        }

    }

}
