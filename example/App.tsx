import { useEffect, useState } from 'react';
import { Pressable, StyleSheet, Text, TextInput, View } from 'react-native';
import * as PianoAnalytics from '@pmu-tech/react-native-piano-analytics';

// TODO: replace with your collection name (string):
const COLLECTION_NAME = '__YOUR_COLLECTION_NAME__';
// TODO: replace with your site id (number):
const SITE_ID = 12345;

// NOTE: Set Piano configuration once:
PianoAnalytics.setConfiguration(COLLECTION_NAME, SITE_ID);

export default function App() {
  const [event, updateEvent] = useState<string>('');
  const [privacyMode, updatePrivacyMode] = useState<PianoAnalytics.PrivacyMode | ''>('');

  useEffect(() => {
    // NOTE: By default Piano will set "no-consent" mode
    updatePrivacyMode(PianoAnalytics.privacyGetMode());
  }, []);

  return (
    <View style={styles.container}>
      <Text>Hi From The Poc Side</Text>
      <TextInput
        style={styles.input}
        onChangeText={(e) => updateEvent(e)}
        value={event}
        placeholder="CD1 Param"
      />
      <Text>{privacyMode}</Text>
      <Pressable
        style={styles.button}
        onPress={() => PianoAnalytics.sendEvent('event.send', { page: event })}
      >
        <Text>Send Event</Text>
      </Pressable>
      <Pressable style={styles.button} onPress={() => PianoAnalytics.privacySetMode('optin')}>
        <Text>Set Privacy Mode to "optin"</Text>
      </Pressable>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#fff',
    alignItems: 'center',
    justifyContent: 'center',
  },
  input: {
    width: '80%',
    height: 40,
    margin: 12,
    borderWidth: 1,
    padding: 10,
  },
  button: {
    alignItems: 'center',
    justifyContent: 'center',
    paddingVertical: 12,
    paddingHorizontal: 32,
    backgroundColor: '#ccc',
    borderRadius: 4,
    elevation: 3,
    width: '80%',
    height: 40,
    margin: 12,
  },
});
