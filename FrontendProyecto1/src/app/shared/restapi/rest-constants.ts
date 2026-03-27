export class RestConstants {
    public readonly API_URL: string = "http://localhost:8080/Proyecto1/api/v1/";

    public getApiUrl(): string {
        return this.API_URL;
    }
}