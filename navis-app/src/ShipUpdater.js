import { loadShips, updatePosition, updatePositions } from './redux/actions';
import { connect } from 'react-redux';
import { useEffect } from 'react';

const ShipUpdater = ({ updatePositions, loadAll }) => {
    useEffect(() => {
        loadAll();
        const worker = new Worker('./updatePositionsWorker.js');
        worker.onmessage = (e) => {
            updatePositions(e.data)
        };
    }, [ loadAll ]);
    return <></>;
};

const mapStateToProps = (state) => ({ ships: state.ships });

const mapDispatchToProps = (dispatch) => ({
    updatePositions: (props) => dispatch(updatePositions(props)),
    loadAll: () => dispatch(loadShips())
});

export default connect(mapStateToProps, mapDispatchToProps)(ShipUpdater);
