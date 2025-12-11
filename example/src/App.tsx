import React, { useState } from 'react';
import { Text, View, StyleSheet, Button } from 'react-native';
import { RequestInAppReview, IsAvailable } from 'react-native-in-app-review-turbo';

export default function App() {
  const [status, setStatus] = useState("Idle");

  const handleReview = async () => {
    try {
      setStatus("Checking availability…");

      const available = await IsAvailable();
      if (!available) {
        setStatus("Review not available on this device.");
        return;
      }

      setStatus("Requesting review…");

      const success = await RequestInAppReview();

      if (success) {
        setStatus("Review flow launched successfully.");
      } else {
        setStatus("Review flow could not be launched.");
      }
    } catch (err) {
      console.warn(err);
      setStatus("Error requesting review.");
    }
  };

  return (
    <View style={styles.container}>
      <Text style={styles.text}>Status: {status}</Text>
      <View style={{ marginTop: 20 }}>
        <Button title="Request In-App Review" onPress={handleReview} />
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    padding: 20,
  },
  text: {
    fontSize: 16,
  },
});
