package conexiones;
//Clase publica permite la conexión
public class AJSONServicio {
    //private String ipGeneral = "http://192.168.1.33:8080/";
    //private String ipGeneral = "http://190.63.2.18:8080/";
    //private String ipGeneral = "http://web.megakons.com.ec:8080/";
    //private String ipGeneral = "http://192.168.0.30/";
    private String ipGeneral = "https://192.168.1.1/";
    //private String ipGeneral = "https://megakons.com:445/";
    private String URL_ENTIDAD_PRINCIPAL = "MEGAKONS_MOVIL_CLIENTE/entidadPrincipal.php";
    private String URL_LOGIN = "MEGAKONS_MOVIL_CLIENTE/login.php";
    private String URL_NOTIFICACION= "MEGAKONS_MOVIL_CLIENTE/notificacion.php";
    private String URL_LISTA_PRODUCTOS = "MEGAKONS_MOVIL_CLIENTE/listaProductos.php";
    private String URL_DETALLE_PRODUCTOS = "MEGAKONS_MOVIL_CLIENTE/detallesProductos.php";
    private String URL_LISTA_NOTAS_PEDIDO = "MEGAKONS_MOVIL_CLIENTE/listaNotasPedido.php";
    private String URL_DETALLE_NOTAS_PEDIDO = "MEGAKONS_MOVIL_CLIENTE/listaNotasDetallePedido.php";
    private String URL_LISTA_PRODUCTOS_OFFLINE = "MEGAKONS_MOVIL_CLIENTE/listaProductosOffline.php";
    private String URL_LISTA_CLIENTES_CARTERAS = "MEGAKONS_MOVIL_CLIENTE/listaClientesCartera.php";
    private String URL_LISTA_CLIENTES_CARTERAS_ANTICIPOS = "MEGAKONS_MOVIL_CLIENTE/listaClientesCarteraAnticipo.php";
    private String URL_LISTA_CLIENTES_CARTERAS_DETALLE = "MEGAKONS_MOVIL_CLIENTE/listaClientesCarteraFacturasNotasDebito.php";
    private String URL_LISTA_CLIENTES_CARTERAS_DETALLE_ABCH = "MEGAKONS_MOVIL_CLIENTE/listaClientesCarteraAbonos.php";
    private String URL_LISTA_CLIENTES_CARTERAS_DETALLE_CHEQUES = "MEGAKONS_MOVIL_CLIENTE/listaClientesCarteraCheques.php";
    private String URL_LISTA_VENDEDORES_POR_OFICINA= "MEGAKONS_MOVIL_CLIENTE/listaVendedoresPorOficinaConsultaPresupuestoVentas.php";
    private String URL_LISTA_PRESUPUESTOS_VENDEDOR_POR_LINEAS = "MEGAKONS_MOVIL_CLIENTE/listaPresupuestosVentasLineasPorVendedorOficinaPresupuestoLineas.php";
    private String URL_LISTA_PRESUPUESTOS_VENDEDOR_POR_LINEAS_NOTAS_CREDITO = "MEGAKONS_MOVIL_CLIENTE/listaPresupuestosVentasLineasPorVendedorOficinaPresupuestoLineasNotasCredito.php";
    private String URL_LISTA_PRESUPUESTOS_VENDEDOR_POR_LINEAS_RENTABILIDAD_VENDEDOR = "MEGAKONS_MOVIL_CLIENTE/listaPresupuestosVentasLineasPorVendedorOficinaPresupuestoLineasRentabilidad.php";
    private String URL_LISTA_PRESUPUESTOS_VENDEDOR_GENERAL = "MEGAKONS_MOVIL_CLIENTE/listaPresupuestosVentasLineasPorOficinaPresupuestoGeneral.php";
    private String URL_LISTA_PRESUPUESTO_VENDEDOR = "MEGAKONS_MOVIL_CLIENTE/listaPresupuestosVentasPorVendedorPorOficina.php";
    private String URL_LISTA_CUENTA_CLIENTES_HASTA_FECHA = "MEGAKONS_MOVIL_CLIENTE/listaPresupuestosVentasPorVendedorPorOficinaCarteraVencidaHastaLaFecha.php";
    private String URL_NUMERO_ITEMS_VENDIDOS_VENDEDOR = "MEGAKONS_MOVIL_CLIENTE/numeroItemsVendidosMesVendedor.php";
    private String URL_BONO_VENTA_RANGO_VENDEDOR = "MEGAKONS_MOVIL_CLIENTE/bonoPorVentaRangoVendedor.php";
    private String URL_BONO_CARTERA_DOCUMENTADA_VENDEDOR = "MEGAKONS_MOVIL_CLIENTE/bonoPorCarteraDocumentadaVendedor.php";
    private String URL_BONO_CARTERA_VENCIDA_VENDEDOR = "MEGAKONS_MOVIL_CLIENTE/bonoPorCarteraVencidaVendedor.php";
    private String URL_BONO_RENTABILIDAD_VENDEDOR = "MEGAKONS_MOVIL_CLIENTE/bonoPorRentabilidadVendedor.php";
    private String URL_BONO_ITEMS_VENDIDOS_VENDEDOR = "MEGAKONS_MOVIL_CLIENTE/bonoPorItemsVendidosVendedor.php";
    private String URL_BONO_ITEMS_BAJA_ROTACION_VENDIDOS_VENDEDOR = "MEGAKONS_MOVIL_CLIENTE/bonoPorItemsBajaRotacionVendidosVendedor.php";
    private String URL_NUMERO_ITEMS_BAJA_ROTACION_VENDIDOS_VENDEDOR = "MEGAKONS_MOVIL_CLIENTE/numeroItemsBajaRotacionVendidosVendedor.php";
    private String URL_LISTA_PRESUPUESTOS_VENDEDOR_POR_LINEAS_BONOS_VENDEDOR = "MEGAKONS_MOVIL_CLIENTE/listaPresupuestosBonosLineasVendedor.php";
    private String URL_COMISION_VENDEDOR_VENTAS_NETAS= "MEGAKONS_MOVIL_CLIENTE/comisionVentasNetasVendedor.php";
    private String URL_COMISION_VENDEDOR_CEMENTO= "MEGAKONS_MOVIL_CLIENTE/comisionVentasCementoVendedor.php";
    private String URL_COMISION_VENDEDOR_HIERRO = "MEGAKONS_MOVIL_CLIENTE/comisionVentasHierroVendedor.php";
    private String URL_RANGOS_BONOS_VENDEDORES_TIPO = "MEGAKONS_MOVIL_CLIENTE/rangoBonosVentasVendedoresTipo.php";
    private String URL_RANGOS_BONOS_VENDEDORES_CV = "MEGAKONS_MOVIL_CLIENTE/rangoBonosVentasVendedoresCarteraVencida.php";
    private String URL_RANGOS_BONOS_VENDEDORES_CD = "MEGAKONS_MOVIL_CLIENTE/rangoBonosVentasVendedoresCarteraDocumentada.php";
    private String URL_RANGOS_BONOS_VENDEDORES_ITEMS = "MEGAKONS_MOVIL_CLIENTE/rangoBonosVentasVendedoresItems.php";
    private String URL_RANGOS_BONOS_VENDEDORES_ITEMS_BAJA_ROTACION = "MEGAKONS_MOVIL_CLIENTE/rangoBonosVentasVendedoresItemsBajaRotacion.php";
    private String URL_RANGOS_BONOS_VENDEDORES_RENTABILIDAD = "MEGAKONS_MOVIL_CLIENTE/rangoBonosVentasVendedoresRentabilidad.php";
    private String URL_RANGOS_BONOS_VENDEDORES_PRESUPUESTO_LINEAS = "MEGAKONS_MOVIL_CLIENTE/rangoBonosVentasVendedoresPresupuestoLineas.php";
    private String URL_DOCUMENTOS_DOCUMENTOS_COMPARTIDOS = "MEGAKONS_MOVIL_CLIENTE/documentosUrlDocumentosCompartidos.php";
    private String URL_DOCUMENTOS_REVISTA_MENSUAL = "MEGAKONS_MOVIL_CLIENTE/documentosUrlRevistaMENSUAL.php";
    private String URL_DOCUMENTOS_TABLA_PROMOCIONES = "MEGAKONS_MOVIL_CLIENTE/documentosUrlTablaPromocionesDescuentosPDF.php";
    private String URL_DOCUMENTOS_REVISTA_MENSUAL_PDF = "MEGAKONS_MOVIL_CLIENTE/documentosUrlRevistaMENSUALPDF.php";
    private String URL_IMAGEN_GIF_PREGUNTA_AQUI = "MEGAKONS_MOVIL_CLIENTE/documentos/imagen_info.gif";
    private String URL_FACTURA_MAESTRO_CLIENTE = "MEGAKONS_MOVIL_CLIENTE/facturaMaestro.php";
    private String URL_FACTURA_DETALLE_CLIENTE = "MEGAKONS_MOVIL_CLIENTE/facturaDetalle.php";

    public String getURL_FACTURA_MAESTRO_CLIENTE() {
        return URL_FACTURA_MAESTRO_CLIENTE ;
    }
    public String getURL_FACTURA_DETALLE_CLIENTE() {
        return URL_FACTURA_DETALLE_CLIENTE;
    }

    public String getURL_DOCUMENTOS_TABLA_PROMOCIONES() {
        return URL_DOCUMENTOS_TABLA_PROMOCIONES;
    }
    public String getURL_NOTIFICACION() {
        return URL_NOTIFICACION;
    }

    public String getURL_IMAGEN_GIF_PREGUNTA_AQUI() {
        return URL_IMAGEN_GIF_PREGUNTA_AQUI;
    }
    public String getURL_DOCUMENTOS_REVISTA_MENSUAL_PDF() {
        return URL_DOCUMENTOS_REVISTA_MENSUAL_PDF;
    }

    public String getURL_DOCUMENTOS_REVISTA_MENSUAL() {
        return URL_DOCUMENTOS_REVISTA_MENSUAL;
    }

    public String getURL_DOCUMENTOS_DOCUMENTOS_COMPARTIDOS() {
        return URL_DOCUMENTOS_DOCUMENTOS_COMPARTIDOS;
    }

    public String getURL_RANGOS_BONOS_VENDEDORES_PRESUPUESTO_LINEAS() {
        return URL_RANGOS_BONOS_VENDEDORES_PRESUPUESTO_LINEAS;
    }

    public String getURL_RANGOS_BONOS_VENDEDORES_ITEMS_BAJA_ROTACION() {
        return URL_RANGOS_BONOS_VENDEDORES_ITEMS_BAJA_ROTACION;
    }

    public String getURL_RANGOS_BONOS_VENDEDORES_RENTABILIDAD() {
        return URL_RANGOS_BONOS_VENDEDORES_RENTABILIDAD;
    }
    public String getURL_RANGOS_BONOS_VENDEDORES_ITEMS() {
        return URL_RANGOS_BONOS_VENDEDORES_ITEMS;
    }
    public String getURL_RANGOS_BONOS_VENDEDORES_CD() {
        return URL_RANGOS_BONOS_VENDEDORES_CD;
    }

    public String getURL_RANGOS_BONOS_VENDEDORES_CV() {
        return URL_RANGOS_BONOS_VENDEDORES_CV;
    }

    public String getURL_RANGOS_BONOS_VENDEDORES_TIPO() {
        return URL_RANGOS_BONOS_VENDEDORES_TIPO;
    }

    public String getURL_COMISION_VENDEDOR_CEMENTO() {
        return URL_COMISION_VENDEDOR_CEMENTO;
    }

    public String getURL_COMISION_VENDEDOR_HIERRO() {
        return URL_COMISION_VENDEDOR_HIERRO;
    }

    public String getURL_COMISION_VENDEDOR_VENTAS_NETAS() {
        return URL_COMISION_VENDEDOR_VENTAS_NETAS;
    }
    public String getURL_LISTA_PRESUPUESTOS_VENDEDOR_POR_LINEAS_BONOS_VENDEDOR() {
        return URL_LISTA_PRESUPUESTOS_VENDEDOR_POR_LINEAS_BONOS_VENDEDOR;
    }
    public String getURL_BONO_ITEMS_BAJA_ROTACION_VENDIDOS_VENDEDOR() {
        return URL_BONO_ITEMS_BAJA_ROTACION_VENDIDOS_VENDEDOR;
    }
    public String getURL_NUMERO_ITEMS_BAJA_ROTACION_VENDIDOS_VENDEDOR() {
        return URL_NUMERO_ITEMS_BAJA_ROTACION_VENDIDOS_VENDEDOR;
    }

    public String getURL_BONO_ITEMS_VENDIDOS_VENDEDOR() {
        return URL_BONO_ITEMS_VENDIDOS_VENDEDOR;
    }
    public String getURL_BONO_CARTERA_VENCIDA_VENDEDOR() {
        return URL_BONO_CARTERA_VENCIDA_VENDEDOR;
    }

    public String getURL_BONO_RENTABILIDAD_VENDEDOR() {
        return URL_BONO_RENTABILIDAD_VENDEDOR;
    }

    public String getURL_BONO_CARTERA_DOCUMENTADA_VENDEDOR() {
        return URL_BONO_CARTERA_DOCUMENTADA_VENDEDOR;
    }

    public String getURL_BONO_VENTA_RANGO_VENDEDOR() {
        return URL_BONO_VENTA_RANGO_VENDEDOR;
    }

    public String getURL_NUMERO_ITEMS_VENDIDOS_VENDEDOR() {
        return URL_NUMERO_ITEMS_VENDIDOS_VENDEDOR;
    }

    public String getURL_LISTA_PRESUPUESTO_VENDEDOR() {
        return URL_LISTA_PRESUPUESTO_VENDEDOR;
    }

    public String getURL_LISTA_CUENTA_CLIENTES_HASTA_FECHA() {
        return URL_LISTA_CUENTA_CLIENTES_HASTA_FECHA;
    }

    public String getURL_LISTA_PRESUPUESTOS_VENDEDOR_GENERAL() {
        return URL_LISTA_PRESUPUESTOS_VENDEDOR_GENERAL;
    }

    public void setURL_LISTA_PRESUPUESTOS_VENDEDOR_GENERAL(String URL_LISTA_PRESUPUESTOS_VENDEDOR_GENERAL) {
        this.URL_LISTA_PRESUPUESTOS_VENDEDOR_GENERAL = URL_LISTA_PRESUPUESTOS_VENDEDOR_GENERAL;
    }

    public String getURL_LISTA_PRESUPUESTOS_VENDEDOR_POR_LINEAS_NOTAS_CREDITO() {
        return URL_LISTA_PRESUPUESTOS_VENDEDOR_POR_LINEAS_NOTAS_CREDITO;
    }

    public String getURL_LISTA_PRESUPUESTOS_VENDEDOR_POR_LINEAS_RENTABILIDAD_VENDEDOR() {
        return URL_LISTA_PRESUPUESTOS_VENDEDOR_POR_LINEAS_RENTABILIDAD_VENDEDOR;
    }

    public String getURL_LISTA_VENDEDORES_POR_OFICINA() {
        return URL_LISTA_VENDEDORES_POR_OFICINA;
    }

    public String getURL_LISTA_PRESUPUESTOS_VENDEDOR_POR_LINEAS() {
        return URL_LISTA_PRESUPUESTOS_VENDEDOR_POR_LINEAS;
    }

    public String getURL_LISTA_CLIENTES_CARTERAS_DETALLE_CHEQUES() {
        return URL_LISTA_CLIENTES_CARTERAS_DETALLE_CHEQUES;
    }

    public String getURL_LISTA_CLIENTES_CARTERAS_ANTICIPOS() {
        return URL_LISTA_CLIENTES_CARTERAS_ANTICIPOS;
    }

    public String getURL_LISTA_CLIENTES_CARTERAS_DETALLE_ABCH() {
        return URL_LISTA_CLIENTES_CARTERAS_DETALLE_ABCH;
    }

    public String getURL_LISTA_PRODUCTOS_OFFLINE() {
        return URL_LISTA_PRODUCTOS_OFFLINE;
    }

    public String getURL_LISTA_CLIENTES_CARTERAS_DETALLE() {
        return URL_LISTA_CLIENTES_CARTERAS_DETALLE;
    }

    public String getURL_ENTIDAD_PRINCIPAL() {
        return URL_ENTIDAD_PRINCIPAL;
    }

    public String getURL_DETALLE_NOTAS_PEDIDO() {
        return URL_DETALLE_NOTAS_PEDIDO;
    }

    public String getURL_LISTA_CLIENTES_CARTERAS() {
        return URL_LISTA_CLIENTES_CARTERAS;
    }

    public String getURL_PaginaWeb() {
        return paginaWeb;
    }

    public String getIpGeneral() {
        return ipGeneral;
    }

    public String getURL_LOGIN() {
        return URL_LOGIN;
    }

    public String getURL_LISTA_PRODUCTOS() {
        return URL_LISTA_PRODUCTOS;
    }

    public String getURL_DETALLE_PRODUCTOS() {
        return URL_DETALLE_PRODUCTOS;
    }

    public String getURL_LISTA_NOTAS_PEDIDO() {
        return URL_LISTA_NOTAS_PEDIDO;
    }

    //UR Rutero
    //private String URL_RUTERO = "http://190.63.2.18/AgendaGPS/";
    //private String URL_RUTERO = "http://192.168.1.5/AgendaGPS/";
    private String paginaWeb = "http://www.megakons.com.ec/";

    /*public String getURL_RUTERO() {
        return URL_RUTERO;
    }*/

}
