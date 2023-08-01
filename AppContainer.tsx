import React from "react";
import { VehiclesScreen } from "./src/screens/VehiclesScreen";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { MD3DarkTheme, MD3LightTheme, PaperProvider } from "react-native-paper";
import { darkColors, lightColors } from "./src/config/colors";
import { useColorScheme } from "react-native";

const queryClient = new QueryClient();

const lightTheme = {
  ...MD3LightTheme,
  colors: lightColors,
};

const darkTheme = {
  ...MD3DarkTheme,
  colors: darkColors,
};

const AppInnerContainer = () => {
  const colorScheme = useColorScheme();

  const theme = colorScheme === "dark" ? darkTheme : lightTheme;

  return (
    <PaperProvider theme={theme}>
      <VehiclesScreen />
    </PaperProvider>
  );
};

export const AppContainer = () => {
  return (
    <QueryClientProvider client={queryClient}>
      <AppInnerContainer />
    </QueryClientProvider>
  );
};
