package como.sas.bootify_app.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ProductoDTO {

    private Integer idProducto;

    @Size(max = 45)
    private String nombre;

    @Size(max = 150)
    private String codigoBarras;

    @Digits(integer = 18, fraction = 2)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal precioVenta;

    @NotNull
    private Integer cantidadStock;

    private Boolean estado;

    @NotNull
    private Integer categoria;

}
