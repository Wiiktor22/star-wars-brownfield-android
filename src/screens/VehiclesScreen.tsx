import React from "react";
import { View, Text, StyleSheet, FlatList, ListRenderItem, ViewStyle } from "react-native";
import { Vehicle, useVehiclesQuery } from "../api/useVehiclesQuery";
import { LoadingScreen } from "./LoadingScreen";
import { Card } from "react-native-paper";

const leftItemStyles: ViewStyle = { width: '90%', marginLeft: 10, marginRight: 5 }
const rightItemStyles: ViewStyle = { width: '90%', marginLeft: 5, marginRight: 10 }

const keyExtractor = (item: Vehicle) => item.name;

const renderItem: ListRenderItem<Vehicle> = ({ item, index }) => (
  <View style={styles.cardContainer}>
    <Card style={index % 2 === 0 ? leftItemStyles : rightItemStyles}>
      {item.photoUri ? <Card.Cover source={{ uri: item.photoUri }} /> : null}
      <Card.Title title={item.name} />
    </Card>
  </View>
);

const ListFooterComponent = () => <View style={styles.footer} />

export const VehiclesScreen = () => {
  const { data, isLoading } = useVehiclesQuery();

  if (isLoading) return <LoadingScreen />;

  return (
    <View style={styles.container}>
      <FlatList
        data={data}
        keyExtractor={keyExtractor}
        renderItem={renderItem}
        numColumns={2}
        ListFooterComponent={ListFooterComponent}
      />
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
  },
  cardContainer: {
    width: "50%",
    alignItems: 'center',
    marginTop: 20
  },
  footer: {
    flex: 1,
    height: 20
  }
});
