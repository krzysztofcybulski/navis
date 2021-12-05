import { connect } from 'react-redux';
import { useEffect, useState } from 'react';

const getLog = ({ mmsi }) => fetch(`http://localhost:8080/events/${mmsi}`)
    .then(r => r.json());

const LogComponent = <div>
</div>

const EventLog = ({ mmsi }) => {

    const [logs, setLogs] = useState([]);

    useEffect(() => {
        if(mmsi) {
            getLog({ mmsi }).then(setLogs);
        }
    }, [mmsi]);

    return <div className="EventLog">
        {logs.map(log => <p key={log.timestamp} className='log'>{JSON.stringify(log, null, 4)}</p>)}
    </div>;
}

const mapStateToProps = (state) => ({ mmsi: state.selected && state.selected.mmsi });

export default connect(mapStateToProps)(EventLog);
