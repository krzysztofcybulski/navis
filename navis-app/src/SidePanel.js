import { connect } from 'react-redux';
import EventLog from './EventLog';

const InfoPanel = ({ title, children }) => children ? <div className="InfoPanel">
    <p className="title">{title}</p>
    <p className="value"> {children}</p>
</div> : <></>;

const ShipInfo = ({ name, mmsi, destination, cog, sog, lastModifiedAt }) => <>
    <img
        src={`https://www.myshiptracking.com/requests/getimage-normal/${mmsi}.jpg`} width="100%"
        onError={(e) => {
            e.target.onerror = null;
            e.target.src = '/ships/ship_illustration.svg';
        }}
    />
    <h1>{name}</h1>
    <hr/>
    <InfoPanel title="Destination">{destination}</InfoPanel>
    <InfoPanel title="Course">{cog}</InfoPanel>
    <InfoPanel title="Speed (knots)">{sog}</InfoPanel>
    <InfoPanel title="Last updated at">{lastModifiedAt.substring(11, 22)}</InfoPanel>
    <EventLog />
</>;

const NoShipSelected = () => <>
    <img width="256px" src="/ships/ship_illustration.svg"/>
    <h2>No ship selected</h2>
</>;

const SidePanel = ({ ship }) => <div className="SidePanel">
    {ship ? <ShipInfo {...ship} /> : <NoShipSelected/>}
</div>;

const mapStateToProps = (state) => ({ ship: state.selected });

export default connect(mapStateToProps)(SidePanel);
