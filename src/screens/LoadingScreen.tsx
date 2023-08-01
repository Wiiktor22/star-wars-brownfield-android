import React from "react";
import { StyleSheet, View } from "react-native";
import { ActivityIndicator, useTheme } from "react-native-paper";

export const LoadingScreen = () => {
    const theme = useTheme()
  return (
    <View style={styles.container}>
      <ActivityIndicator color={theme.colors.primary} size='large'/>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
  },
});
