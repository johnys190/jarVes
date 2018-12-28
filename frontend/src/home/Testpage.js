import React, { Component } from 'react';
import { withRouter } from 'react-router-dom';
import LoadingIndicator from '../common/LoadingIndicator';
import './Menu.css';
import { renderID } from '../util/Helpers';
import { 
    Radio,
    Input,
    DatePicker,
    Select,
    Button,
    Row,
    Col
} from 'antd';
import { 
    getVesselById,
    getAllVessels,
    getAllVoyEstimates,
    getAllTCEstimates
} from '../util/APIUtils';

const Option = Select.Option;

class VoyageEstimate extends Component {
	constructor(props) {
        super(props);
        this.state = {
            isLoading: false,
            new: false,
            vesselId: 1
        };
        this.getAllVessels = this.getAllVessels.bind(this);
        this.getAllVoyEstimates = this.getAllVoyEstimates.bind(this);
        this.getAllTCEstimates = this.getAllTCEstimates.bind(this);
    }

    componentWillMount(){
        this.getAllVessels();
        this.getAllVoyEstimates();
        this.getAllTCEstimates();
    }

    calculate(){
        if (this.state.hirerate && this.state.ballastbonus){
            this.setState({
                timecharterrate:  Number(this.state.hirerate) + Number(this.state.ballastbonus)
            })
        }
    }

    getVesselById(){
        this.setState({
            isLoading: true
        });
        let promise;

        promise = getVesselById(this.state.vesselId);

        if (!promise) {
            return;
        }

        promise
            .then(response => {
                console.log(response)
                // this.setState({
                //     vessels: response ? response : [],
                //     isLoading: false
                // })
            }).catch(error => {
            this.setState({
                isLoading: false
            })
        });
    }

    getAllVessels(){
        this.setState({
            isLoading: true
        });
        let promise;

        promise = getAllVessels();

        if (!promise) {
            return;
        }

        promise
            .then(response => {
                console.log('vessels');
                console.log(response)
                this.setState({
                    //vessels: response ? response : [],
                    isLoading: false
                })
            }).catch(error => {
            this.setState({
                isLoading: false
            })
        });
    }

    getAllTCEstimates(){
        this.setState({
            isLoading: true
        });
        let promise;

        promise = getAllTCEstimates();

        if (!promise) {
            return;
        }

        promise
            .then(response => {
                console.log('tcestimates');
                console.log(response)
                this.setState({
                    //vessels: response ? response : [],
                    isLoading: false
                })
            }).catch(error => {
            this.setState({
                isLoading: false
            })
        });
    }

    getAllVoyEstimates(){
        this.setState({
            isLoading: true
        });
        let promise;

        promise = getAllVoyEstimates();

        if (!promise) {
            return;
        }

        promise
            .then(response => {
                console.log('voyages')
                console.log(response);
                this.setState({
                    //voys: response ? response : [],
                    isLoading: false
                })
            }).catch(error => {
            this.setState({
                isLoading: false
            })
        });
    }

    handleChangeInput = (e) => {
        const id = e.target.id;
        const value = e.target.value;
        this.setState({
            [id]: value
            },
            this.calculate
        );
    }

    handleChangeRadio = (e) => {
        const id = e.target.name;
        const value = e.target.value;
        this.setState({
            [id]: value
            },
            this.calculate
        );
    }

    handleChangeSelect = (e) => {
        this.setState({
            voyage: e
        })
    }

    handleNewCancelClick = () => {
        this.setState({
            new: !this.state.new
        });
    }

    handleSave = () => {
        console.log(this.state);
    }

	render(){
        if(this.state.isLoading) {
            return <LoadingIndicator />
        }

		return (
            <div>
            check: 
            {this.state.vessels}
            </div>
			);
	}
}
export default withRouter(VoyageEstimate);



