package metodosGenerales;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.mobile.megakons.sa.ClienteCarteraActivity;
import com.mobile.megakons.sa.LoginActivity;
import com.mobile.megakons.sa.MenuActivity;
import com.mobile.megakons.sa.MenuLineasProductosActivity;
import com.mobile.megakons.sa.NotasPendientesActivity;
import com.mobile.megakons.sa.PaginaWebActivity;
import com.mobile.megakons.sa.ProductosOfflineActivity;
import com.mobile.megakons.sa.R;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class General {

    //Cerrar Activity actual y abrir el menu principal
    public static void abrirIntentMenuPrincipal(Activity actividadPaginaActual, Context contextActividadActual){
        Intent intent = new Intent(contextActividadActual, MenuActivity.class);
        actividadPaginaActual.startActivity(intent);
        actividadPaginaActual.finish();
    }
    //Cerrar Activity actual y abrir el menu de lineas de articulos
    public static void abrirIntentMenuLineasProductos(Activity actividadPaginaActual, Context contextActividadActual){
        Intent intent = new Intent(contextActividadActual, MenuLineasProductosActivity.class);
        actividadPaginaActual.startActivity(intent);
        actividadPaginaActual.finish();
    }
    //Cerrar Activity actual y abrir las notas de Pedidos
    public static void abrirIntentNotasPedidos(Activity actividadPaginaActual, Context contextActividadActual){
        Intent intent = new Intent(contextActividadActual, NotasPendientesActivity.class);
        actividadPaginaActual.startActivity(intent);
        actividadPaginaActual.finish();
    }
    //Cerrar Activity actual y abrir la lista de precios total
    public static void abrirIntentProductosTotal(Activity actividadPaginaActual, Context contextActividadActual){
        Intent intent = new Intent(contextActividadActual, ProductosOfflineActivity.class);
        actividadPaginaActual.startActivity(intent);
        actividadPaginaActual.finish();
    }
    //Cerrar Activity actual y abrir la lista de clientes
    public static void abrirIntentClientes(Activity actividadPaginaActual, Context contextActividadActual){
        Intent intent = new Intent(contextActividadActual, ClienteCarteraActivity.class);
        actividadPaginaActual.startActivity(intent);
        actividadPaginaActual.finish();
    }

    //Cerrar Activity actual y abrir la pagina Web
    public void abrirIntentPaginaWeb(Activity actividadPaginaActual, Context contextActividadActual){
        Intent intent = new Intent(contextActividadActual, PaginaWebActivity.class);
        actividadPaginaActual.startActivity(intent);
        actividadPaginaActual.finish();
    }

    //Cerrar la sesion inicial y finalizar el activity
    public static void cerrarSesion(Activity actividadPaginaActual, Context contextActividadActual, SharedPreferences preferencesActividad){
        //preferences.edit().clear().commit();
        preferencesActividad.edit().clear().apply();
        Intent intent = new Intent(contextActividadActual, LoginActivity.class);
        actividadPaginaActual.finishAffinity();
        actividadPaginaActual.finish();
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        actividadPaginaActual.startActivity(intent);
        //System.exit(0);
    }

    //Obtener parte de una cadena del TextView
    public static String obtenerSubstringCadena(String cadenaCompleta, int desde, int hasta){
            String cadena = cadenaCompleta.substring(desde, hasta);
        return cadena;
    }

    //Aumentar el 0 como centecimo de un numero por ejemplo 10.5 -> 10.50 en un textview
    public static String aumentarCeroNumeroUnSoloDecimal(Double numero){
        String numeroString = String.valueOf(numero);
        //int intNumber = Integer.parseInt(numeroString.substring(0, numeroString.indexOf('.')));
        //float decNumbert = Float.parseFloat(numeroString.substring(numeroString.indexOf('.')));
        //int decNumberInt = Integer.parseInt(numeroString.substring(numeroString.indexOf('.') + 1));
        String decNumberInt = numeroString.substring(numeroString.indexOf('.') + 1);
        if (decNumberInt.length() == 1){
            return numero.toString() + "0";
        }
        return  numero.toString();
    }


    public void enviaMensajeWhatsApp(String numeroTel,String msj, Context context) {
        try{
            Intent intent = new Intent(Intent.ACTION_VIEW);
            String uri = "whatsapp://send?phone=" + numeroTel + "&text=" + msj;
            intent.setData(Uri.parse(uri));
            context.startActivity(intent);
        }catch(RuntimeException e){
            Toast.makeText(context, "Por favor, instale la aplicación de Whatsapp.", Toast.LENGTH_LONG).show();
        }
    }


    //Truncar valores de un Decimal con valor 0 a Entero por ejemplo si es 6.00 => 6
    public static String truncarNumero(Double valorAtruncar){
        String numeroString = String.valueOf(valorAtruncar);
        String decNumberInt = numeroString.substring(numeroString.indexOf('.') + 1);
        if (decNumberInt.length() == 1 && decNumberInt.equals("0")){
                return String.valueOf(Math.round(valorAtruncar));
            }else if (decNumberInt.length() > 2){
                return  valorAtruncar.toString();

        }
        return  valorAtruncar.toString() + "0";
    }

    //Truncar valores de Tres o Más Decimales a Dos Decimales por ejemplo si es 6.123 -> 6.12
    public static String redondearNumero(Double valorAredondear){
        //Libreria BigDecimal de la biblioteca Math para redondear Double a dos decimales
        BigDecimal bd = new BigDecimal(valorAredondear).setScale(2, RoundingMode.HALF_UP);
        //double valorRedondeadoDosDecimales = bd.doubleValue();
        String valorRedondeadoDosDecimales = String.valueOf(bd.doubleValue());
        //Operaciones Dispuestas a los metodos truncar Numero y aumentarCeroNumeroUnSoloDecimal
        String numeroString = String.valueOf(valorAredondear);
        String decNumberInt = numeroString.substring(numeroString.indexOf('.') + 1);
        if (decNumberInt.length() == 1){ //103269
            return valorAredondear.toString() + "0";
        }else if(decNumberInt.substring(1,2).contentEquals("0")){
            return  valorRedondeadoDosDecimales + "0";
        }
        return  valorRedondeadoDosDecimales;
    }

    //Truncar valores de Tres o Más Decimales a Dos Decimales por ejemplo si es 6.1234 -> 6.123
    public static String redondearNumeroA3Decimales(Double valorAredondear){
        //Libreria BigDecimal de la biblioteca Math para redondear Double a tres decimales
        BigDecimal bd = new BigDecimal(valorAredondear).setScale(3, RoundingMode.HALF_UP);
        //double valorRedondeadoDosDecimales = bd.doubleValue();
        String valorRedondeadoDosDecimales = String.valueOf(bd.doubleValue());
        //Operaciones Dispuestas a los metodos truncar Numero y aumentarCeroNumeroUnSoloDecimal
        String numeroString = String.valueOf(valorAredondear);
        String decNumberInt = numeroString.substring(numeroString.indexOf('.') + 1);
        if (decNumberInt.length() == 1){
            return valorAredondear.toString() + "0";
        }
        return  valorRedondeadoDosDecimales;
    }

    //Conversion de Date a String dd / MM / yyyy
    public Date conversionStringDate(String fecha) throws ParseException {
        Date fechaConvertida = new SimpleDateFormat("dd/MM/yyyy").parse(fecha);
        return fechaConvertida;
    }

    //Conversion de String a Fecha dd/MM/yyyy
    public String conversionDateString(Date fecha){
        SimpleDateFormat sdf = new SimpleDateFormat("dd / MM / yyyy");
        String fechaConvertida = sdf.format(fecha);
        return fechaConvertida;
    }

    //Conversion de String a Fecha dd/MM/yyyy
    public Date conversionStringDate2(String fecha){
        SimpleDateFormat formato = new SimpleDateFormat("dd-MMM-yy");
        Date fechaConvertida = null;
        try {
            fechaConvertida = formato.parse(fecha);
            return fechaConvertida;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return fechaConvertida;
    }

    // Suma años recibidos a la fecha
    public Date sumaAniosFecha(Date fecha, int anios){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha); // Configuramos la fecha que se recibe
        calendar.add(Calendar.YEAR, anios);  // numero de anios a añadir, o restar en caso de anios<0
        return calendar.getTime(); // Devuelve el objeto Date con los nuevos anios añadidas
    }

    public Date ParsearFecha(String fecha)
    {
        SimpleDateFormat formato = new SimpleDateFormat("dd / MM / yyyy");
        Date fechaDate = null;
        try {
            fechaDate = formato.parse(fecha);
        }
        catch (ParseException ex)
        {
            System.out.println(ex);
        }
        return fechaDate;
    }

    //Doble con dos decimales
    public static String conversionDosDecimalesDoubleString(double numero){
        DecimalFormat df = new DecimalFormat("###.##");
        return df.format(numero);
    }


    //Mostrar y esconder opciones segun reglas de incio de sesión del usuario MENU PRINCIPAL
    public void reglasMenuPrincipal(SharedPreferences preferences, LinearLayout linearLayout2, LinearLayout linearLayout3){
        String verificacionUsuario = preferences.getString("verificacionUsuario", null);
        if(verificacionUsuario.contentEquals("2")){
            //Ocultar la informacion para almacen
            linearLayout2.setVisibility(View.GONE);
            linearLayout3.setVisibility(View.GONE);
        }else if(verificacionUsuario.contentEquals("1")){
            //Ocultar la informacion para SUPERVISORES DE VENTAS
            linearLayout2.setVisibility(View.GONE);
        }
    }

    //Mostrar y esconder opciones segun reglas de incio de sesión del usuario MENU SECUNDARIO LATERAL
    public void reglasMenuSecundario(NavigationView navigationView, SharedPreferences preferences,String verificacionUsuario ){
        //Menu
        Menu menu = navigationView.getMenu();
        if(verificacionUsuario.contentEquals("2")){
            //Ocultar la informacion para almacen
            menu.findItem(R.id.nav_notaspendientes).setVisible(false);
            menu.findItem(R.id.nav_clientes).setVisible(false);
            menu.findItem(R.id.nav_Kpis).setVisible(false);
            menu.findItem(R.id.nav_Documentos).setVisible(false);
        }else if(verificacionUsuario.contentEquals("1")){
            //Ocultar la informacion para SUPERVISORES DE VENTAS
            menu.findItem(R.id.nav_notaspendientes).setVisible(false);
            menu.findItem(R.id.nav_clientes).setVisible(false);
        }
    }

    //Mostrar y esconder opciones segun reglas de incio de sesión del usuario MENU KPIS Ventas
    public void reglasMenuKPISVentas(SharedPreferences preferences, LinearLayout linearLayout1, LinearLayout linearLayout2){
        String verificacionUsuario = preferences.getString("verificacionUsuario", null);
        if(verificacionUsuario.contentEquals("1")){
            //Ocultar la informacion para almacen
            linearLayout2.setVisibility(View.GONE);
        }else if(verificacionUsuario.contentEquals("0")){
            //Ocultar la informacion para SUPERVISORES DE VENTAS
            linearLayout1.setVisibility(View.GONE);
        }
    }

    //Mostrar un mensaje corto utilizando TOAST
    public void mensajeToastCorto(Context context, String mensaje){
        Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show();
    }

    //Conversion de datos de tipo texto String a Calendar
    public Calendar conversionStringCalendar(String fecha_DD_MM_YYYY){
        Calendar fechaCalendarioConvertida = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd / MM / yyyy");
        try {
            fechaCalendarioConvertida.setTime(sdf.parse(fecha_DD_MM_YYYY));// all done
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return fechaCalendarioConvertida;
    }

    //Obtener numero del ultimo dia de mes
    public String obetenerNumeroUltimoDiaMes(String fecha_DD_MM_YYYY) {
        //Calculo dia fin de mes
        String mes_ultimo_dia_mes = fecha_DD_MM_YYYY.substring(5,7);
        String anio_ultimo_dia_mes = fecha_DD_MM_YYYY.substring(10,14);
        Calendar calFinMes = Calendar.getInstance();
        calFinMes.set(Calendar.MONTH, Integer.valueOf(mes_ultimo_dia_mes)-1);
        calFinMes.set(Calendar.YEAR, Integer.valueOf(anio_ultimo_dia_mes));
        int ultimoDiaMes = calFinMes.getActualMaximum(Calendar.DAY_OF_MONTH);
        String numeroUltimoDiaMes = String.valueOf(ultimoDiaMes);
        if (numeroUltimoDiaMes.length() == 1){
            numeroUltimoDiaMes = "0" + numeroUltimoDiaMes;
        }
        return numeroUltimoDiaMes;
    }

    //Control
    public String validacionFecha1MayorMenorAFecha2(String fecha1_DD_MM_YYYY, String fecha2_DD_MM_YYYY){
        String respuesta = ""; // respuesta Fecha 1 es Mayor o Fecha 1 es Menoe @@ a fecha 2
        SimpleDateFormat date = new SimpleDateFormat("dd / MM / yyyy");
        Date fecha1Date = null;  //String a date
        Date fecha2Date = null;
        try {
            fecha1Date = date.parse(fecha1_DD_MM_YYYY);
            fecha2Date = date.parse(fecha2_DD_MM_YYYY);  //String a date
            //comprueba si es que inicio esta después que fecha actual
            if(fecha1Date.after(fecha2Date)){
                return respuesta = "MENOR";
            }else{
                return respuesta = "MAYOR";
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return respuesta;
    }

    //FORMATO MONEDA DESDE STRING
    public String string_Moneda(Double numero){
        NumberFormat formatoImporte = NumberFormat.getCurrencyInstance(new Locale("es","EC"));
        String aux = formatoImporte.format(numero).toString();
        return aux;
    }
    //FORMATO PORCENTAJE DESDE STRING
    public String string_Porcentaje(Double numero){
        NumberFormat formatoPorcentaje = NumberFormat.getPercentInstance();
        formatoPorcentaje.setMinimumFractionDigits(2);
        return formatoPorcentaje.format(numero);
    }

}
