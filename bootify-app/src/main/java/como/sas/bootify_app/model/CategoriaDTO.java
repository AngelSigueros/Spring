package como.sas.bootify_app.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CategoriaDTO {

    private Integer idCategoria;

    @NotNull
    @Size(max = 45)
    private String descripcion;

    @NotNull
    private Boolean estado;

}
