import './App.css';
import { MapContainer } from 'react-leaflet';
import ShipUpdater from './ShipUpdater';
import Map from './Map';
import SidePanel from './SidePanel';

const App = () => {
    return <>
        <ShipUpdater/>
        <div className="App">
            <MapContainer
                center={[52, 16]}
                zoom={9}
                zoomControl={false}
                style={{ height: '100vh', width: '100%' }}
            >
                <Map/>
            </MapContainer>
            <SidePanel />
        </div>
    </>;
};

export default App;
