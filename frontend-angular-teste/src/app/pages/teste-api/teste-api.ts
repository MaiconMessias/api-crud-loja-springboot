import { Component, inject, OnInit } from "@angular/core";
import { Api } from '../services/api';

@Component({
  imports: [],
  selector: "app-teste-api",
  styleUrl: "./teste-api.css",
  templateUrl: "./teste-api.html",
})
export class TesteApi implements OnInit {
  
  private api = inject(Api);

  dados: any;

  ngOnInit() {
    this.api.listarProdutos().
  }

}
