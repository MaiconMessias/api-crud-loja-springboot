import { ComponentFixture, TestBed } from "@angular/core/testing";
import { TesteApi } from "./teste-api";

describe("TesteApi", () => {
  let component: TesteApi;
  let fixture: ComponentFixture<TesteApi>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TesteApi],
    }).compileComponents();

    fixture = TestBed.createComponent(TesteApi);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });
});
