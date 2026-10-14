import { Service, inject } from "@angular/core";
import { HttpClient } from "@angular/common/http";

@Service()
export class Api {

    private http = inject(HttpClient);

    listarProdutos()  {
        return this.http.get('/api/listaproduto');
    }
}
