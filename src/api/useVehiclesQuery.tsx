import { useQuery } from "@tanstack/react-query";
import { getVehiclePhotoUri } from "./utils";

interface FetchedVehicle {
  name: string;
  model: string;
  manufacturer: string;
  cost_in_credits: string;
  length: string;
  max_atmosphering_speed: string;
  crew: string;
  passengers: string;
  cargo_capacity: string;
  consumables: string;
  vehicle_class: string;
  films: string[];
  created: string;
  edited: string;
  url: string;
}

export interface Vehicle extends FetchedVehicle{
  photoUri: string | null;
}

interface VehiclesQueryResponse {
  results: FetchedVehicle[];
}

const singleFetchMethod = async (url: string) => {
    const response = await fetch(url);
    return await response.json() as Awaited<VehiclesQueryResponse>
}

const queryKey = ["vehicles"];
const queryFn = async (): Promise<Vehicle[]> => {
  const response = await Promise.all([
    singleFetchMethod("https://swapi.dev/api/vehicles/?page=1"),
    singleFetchMethod("https://swapi.dev/api/vehicles/?page=2"),
    singleFetchMethod("https://swapi.dev/api/vehicles/?page=3"),
  ]);

  return response.flatMap(r => r.results).map(vehicle => ({
    ...vehicle,
    photoUri: getVehiclePhotoUri(vehicle.url)
  }))
};

export const useVehiclesQuery = () =>
  useQuery({
    queryKey,
    queryFn,
  });
