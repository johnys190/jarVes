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
    Col,
    Form,
    notification
} from 'antd';
import { 
    getVesselById,
    getAllVessels,
    getVoyEstimateById,
    updateVoyEstimateById,
    createVoyEstimate
} from '../util/APIUtils';

const Option = Select.Option;
const FormItem = Form.Item;
const formItemLayout = {
      labelCol: {
        xs: { span: 24 },
        sm: { span: 12 },
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 12 },
      }
};

class VoyageEstimate extends Component {
	constructor(props) {
        super(props);
        this.state = {
            isLoading: false,
            vesselId: 1,
            vessels: [],
            voyest:{
                id: this.props.id ? this.props.id : 'new',
                account: '',
                broker: '',
                commodity: '',
                name: '',
                vessel: null,
                voyage: ''
            }

        };
        this.getAllVessels();
        //this.getAllVessels = this.getAllVessels.bind(this);
        if (this.state.voyest.id !== 'new'){
            this.getVoyageEstimate();
        }
    }

    componentWillMount(){
        this.getAllVessels();
    }

    calculate(){
        const voyestEdit = this.state.voyest;

        if ( voyestEdit.freight_rate_type == 'lumpsum' ) {
            voyestEdit.gross_revenue = voyestEdit.freight_rate
        } else { 
            voyestEdit.gross_revenue = voyestEdit.freight_rate * voyestEdit.quantity
        }

        voyestEdit.sailing_bunkers = (voyestEdit.otal_ballast_distance - voyestEdit.seca_ballast_distance) * voyestEdit.ifo_ballast * voyestEdit.ifo_price + voyestEdit.seca_ballast_distance * voyestEdit.ifo_ballast * voyestEdit.mgo_price + (voyestEdit.total_laden_distance - voyestEdit.seca_laden_distance) * voyestEdit.ifo_laden * voyestEdit.ifo_price + voyestEdit.seca_laden_distance * voyestEdit.ifo_laden * voyestEdit.mgo_price;

        if (voyestEdit.seca &&(voyestEdit.load_port in voyestEdit.seca)){
            voyestEdit.loadport_bunkers = voyestEdit.ifo_port_work * voyestEdit.load_days * voyestEdit.mgo_price + voyestEdit.ifo_port_idle * voyestEdit.shex_load * voyestEdit.mgo_price + voyestEdit.mgo_port_work * voyestEdit.load_days * voyestEdit.mgo_price + voyestEdit.mgo_port_idle * voyestEdit.shex_load * voyestEdit.mgo_price + (voyestEdit.load_days + voyestEdit.shex_load) * voyestEdit.boiler_port * voyestEdit.mgo_price;
        }

        
        if (voyestEdit.non_seca && voyestEdit.load_port in voyestEdit.non_seca){
            voyestEdit.loadport_bunkers = voyestEdit.ifo_port_work * voyestEdit.load_days * voyestEdit.ifo_price + voyestEdit.ifo_port_idle * voyestEdit.shex_load * voyestEdit.ifo_price + voyestEdit.mgo_port_work * voyestEdit.load_days * voyestEdit.mgo_price + voyestEdit.mgo_port_idle * voyestEdit.shex_load * voyestEdit.mgo_price + (voyestEdit.load_days + voyestEdit.shex_load) * voyestEdit.boiler_port * voyestEdit.ifo_price;
        }

        // voyestEdit.discharge_port_bunkers(){
        // if load_port in seca  ifo_port_work * discharge_days * mgo_price + ifo_port_idle * shex_discharge_days * mgo_price + mgo_port_work * discharge_days * mgo_price + mgo_port_idle * shex_discharge_days * mgo_price + (discharge_days + shex_discharge_days) * boiler_port * mgo_price
        // }

        // voyestEdit.discharge_port_bunkers(){
        // if load_port in non_seca  ifo_port_work * discharge_days * ifo_price + ifo_port_idle * shex_discharge_days * ifo_price + mgo_port_work * discharge_days * mgo_price + mgo_port_idle * shex_discharge_days * mgo_price + (discharge_days + shex_discharge_days) * boiler_port * ifo_price
        // }

        // voyestEdit.total_bunker_cost(){
        // loadport_bunkers + discharge_port_bunkers + sailing_bunkers + lost_waiting_days * ( ifo_port_idle * ifo_price + mgo_port_idle * mgo_price + boiler_port * ifo_price)
        // }

        // voyestEdit.expenses(){
        // load + disch + others + canals + exins + (comm x gross_revenue)
        // }

        // voyestEdit.taxes(){
        // taxes x gross_revenue
        // }

        // voyestEdit.exins(){
        // exins
        // }

        // voyestEdit.net_revenue(){
        // gross_revenue - expenses - taxes - total_bunker_cost
        // }

        // voyestEdit.time_charter_rate(){
        // net_revenue / total_duration_days
        // }

        // voyestEdit.steaming_days(){
        // ( total_ballast_distance + total_laden_distance ) / ( speed * 24 ) + steaming_margin x ( total_ballast_distance + total_laden_distance ) / ( speed * 24 )
        // }

        // voyestEdit.load_days(){
        // if ( load_rate_type == DAPS ) { load_days = load_rate} else { load_days = quantity / load_rate } 
        // }

        // voyestEdit.discharge_days(){
        // if ( discharge_rate_type == DAPS ) { discharge_days = discharge_rate} else { discharge_days = quantity / discharge_rate } 
        // }

        // voyestEdit.shex_load(){
        // if ( load_rate_type == X) { shex_load = load_days * lost_waiting_days} else { shex_load = 1 } 
        // }

        // voyestEdit.shex_discharge_days(){
        // if ( discharge_rate_type == X) { shex_discharge_days = discharge_days * lost_waiting_days} else { shex_discharge_days = 1 } 
        // }

        voyestEdit.total_duration_days = voyestEdit.steaming_days + voyestEdit.load_days + voyestEdit.discharge_days + voyestEdit.shex_load + voyestEdit.shex_discharge_days + voyestEdit.lost_waiting_days;
    

        this.setState({
            voyest:voyestEdit
        });
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
                this.setState({
                    vessels: response ? response : [],
                    isLoading: false
                })
            }).catch(error => {
            this.setState({
                isLoading: false
            })
        });
    }

    getVoyageEstimate(){
        this.setState({
            isLoading: true
        });
        let promise;

        promise = getVoyEstimateById(this.state.voyest.id);

        if (!promise) {
            return;
        }

        promise
            .then(response => {
                this.setState({
                    voyest:response,
                    isLoading: false
                });
                console.log(this.state);
            }).catch(error => {
            this.setState({
                isLoading: false
            })
        });
    }

    handleSubmit() {
        this.setState({
            isLoading: true,
        });

        let voyest = this.state.voyest;
        let promise;
        console.log(this.state);
        if (this.state.voyest.id !== 'new'){

            promise = updateVoyEstimateById(this.state.voyest.id, voyest);
            promise
                .then(response => {
                    notification.success({
                        message: 'Seminar App',
                        description: "Sucessfully saved changes!",
                    });
                    this.setState({
                        isLoading: false
                    });
                })
                .catch(error => {
                    notification.error({
                        message: 'Seminar App',
                        description: error.message || 'Sorry! Something went wrong. Please try again!'
                    });
                    this.setState({
                        isLoading: false
                    });
                });
        }else{
            delete voyest['id'];
            promise = createVoyEstimate(voyest);
            promise
                .then(response => {
                    notification.success({
                        message: 'Seminar App',
                        description: "Sucessfully created!",
                    });

                    this.setState({
                        voyest: response,
                        isLoading: false
                    });
                })
                .catch(error => {
                    notification.error({
                        message: 'Seminar App',
                        description: error.message || 'Sorry! Something went wrong. Please try again!'
                    });
                    this.setState({
                        isLoading: false
                    });
                });
        }
        console.log(this.state);
    }

    handleSubmitAs() {
        this.setState({
            isLoading: true,
        });

        let voyest = this.state.voyest;
        let curName = voyest.name;
        voyest.name = voyest.save_as;
        delete voyest['id'];
        let promise;
        promise = createVoyEstimate(voyest);
        promise
            .then(response => {
                notification.success({
                    message: 'Seminar App',
                    description: "Sucessfully saved!",
                });
                this.setState({
                    voyest: response,
                    isLoading: false
                });
            })
            .catch(error => {
                notification.error({
                    message: 'Seminar App',
                    description: error.message || 'Sorry! Something went wrong. Please try again!'
                });
                this.setState({
                    isLoading: false
                });
            });
    }



    handleInputChange(event) {
        const target = event.target;
        const inputName = target.name;
        const inputValue = target.value;
        const voyestEdit = this.state.voyest;
        voyestEdit[inputName]= inputValue;
        this.setState({
            voyest:voyestEdit
        });
        this.calculate();
        console.log(this.state);
    }

    // handleChangeInput = (e) => {
    //     const id = e.target.id;
    //     const value = e.target.value;
    //     this.setState({
    //         [id]: value
    //         },
    //         this.calculate
    //     );
    // }

    // handleChangeRadio = (e) => {
    //     const id = e.target.name;
    //     const value = e.target.value;
    //     this.setState({
    //         [id]: value
    //         },
    //     );
    // }

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

    renderInputList(fields, className='', disabled=false){
        return fields.map( (field) => (
             <Input 
                className={className}
                addonBefore={field} 
                name={renderID(field)}
                onChange={(event) => this.handleInputChange(event)}
                disabled={disabled}
                defaultValue={this.state.voyest[renderID(field)]}/> 
        ))
    }


    renderRadioList(fields, id, style={}, className='alignRadioGroup'){
        const RadioGroup = Radio.Group;
        return (
            <RadioGroup 
                className={className} 
                name={id}
                onChange={this.handleInputChange.bind(this)}>
                    {fields.map( (field) => (
                        <Radio  
                            style={style} 
                            value={renderID(field)}>
                                {field}
                        </Radio> 
                    ))}
            </RadioGroup>
        )
    }
	render(){
        if(this.state.isLoading) {
            return <LoadingIndicator />
        }

        const veerticalRadioStyle = {
            display: 'block',
            height: '30px',
            lineHeight: '30px',
        };

        const vesselsOptions = this.state.vessels.map((vessel) => {
                                        return (<Option value={vessel.id}>{vessel.name}</Option>);
                                    });
        const saveAs = (this.state.voyest.id && this.state.voyest.id !== 'new') ?
            (<div>
                <Input addonBefore='Save as' name='save_as'  onChange={(event) => this.handleInputChange(event)} defaultValue={this.state.voyest.save_as}/>
                <Button className='button' type='primary' onClick={this.handleSubmitAs.bind(this)}>Save as</Button>
            </div>) : '';
		return (
            <div>
                <Row gutter={15}>
                    <Col  xs={24} sm={24} md={24} lg={24} xl={12}>
                        <Input addonBefore='Name' name='name' defaultValue={this.state.voyest.name}  onChange={(event) => this.handleInputChange(event)}/>
                        <Button className='button' type='primary' onClick={this.handleSubmit.bind(this)}>Save</Button>
                        {saveAs}
                        <Row gutter={15}>
                            <Col  xs={24} sm={12} md={12} lg={12} xl={12}>
                                {this.renderInputList(['Voyage', 'Account', 'Commodity', 'Broker', 'Laycan', 'Quantity', 'Freight rate'])}
                                <br />
                                {this.renderRadioList(['LUMPSUM', 'Per MT Intake', 'Per LT Intake'], 'freight_rate_type', veerticalRadioStyle)}
                            </Col>
                            <Col  xs={24} sm={12} md={12} lg={12} xl={12}>
                                <Row>
                                    <Col span={12}>
                                        <Input className='alignComponent' addonBefore='L/Rate' name='lrate' onChange={(event) => this.handleInputChange(event)}/>
                                    </Col>
                                    <Col offset={1} span={11}>
                                     {this.renderRadioList(['X','C'],'lrateRadio')}
                                    </Col>
                                </Row>
                                <Row>
                                    <Col span={12}>
                                        <Input className='alignComponent' addonBefore='D/Rate' name='drate' onChange={(event) => this.handleInputChange(event)}/>
                                    </Col>
                                    <Col offset={1} span={11}>
                                        {this.renderRadioList(['X','C'],'drateRadio')}
                                    </Col>
                                </Row>
                                {this.renderInputList(['Comm.', 'Repos.'])}
                                <DatePicker addonBefore='Date' name='date' />
                            </Col>
                        </Row>

                        <br />

                        <Row gutter={15}>
                            <Col  xs={24} sm={12} md={12} lg={12} xl={12}>
                                <p>Ballast distance</p>
                                {this.renderInputList(['NON Seca (Ballast)', 'Seca (Ballast)'])}
                                <p>Laden distance</p>
                                {this.renderInputList(['NON Seca (Laden)', 'Seca (Laden)', 'Lfo price', 'Mgo price', 'Lost/waiting days'])}
                            </Col>
                            <Col  xs={24} sm={12} md={12} lg={12} xl={12}>
                                <p>Port costs</p>
                                {this.renderInputList(['Load', 'Disch', 'Others', 'Canals', 'Taxes %', 'Miscel.', 'Exins'])}
                            </Col>
                        </Row>
                    </Col>

                    <Col  xs={24} sm={24} md={24} lg={24} xl={12}>
                        <span class="ant-input-group-wrapper">
                            <span class="ant-input-wrapper ant-input-group">
                                <span class="ant-input-group-addon">
                                    Vessel
                                </span>
                                <Select
                                    className='alignSelect'
                                    showSearch
                                    placeholder="Select a ship"
                                    optionFilterProp="children"
                                    onChange={this.handleChange}
                                    onFocus={this.handleFocus}
                                    onBlur={this.handleBlur}
                                    filterOption={(input, option) => option.props.children.toLowerCase().indexOf(input.toLowerCase()) >= 0}>
                                        {vesselsOptions}
                                </Select>
                            </span>
                        </span>
                        <Row gutter={15}>
                            <Col  xs={24} sm={12} md={12} lg={12} xl={12}>
                                {this.renderInputList(['Speed', 'Ifo Ballast', 'Ifo Laden', 'Mdo Sea', 'IFO port idle', 'IFO port work', 'MGO port idle', 'MGO port work', 'Boiler port'])}
                            </Col>
                            <Col  xs={24} sm={12} md={12} lg={12} xl={12}>
                                {this.renderInputList(['Load port', 'Disch. port', 'Streaming margin'])}
                                <p>Days</p> 
                                {this.renderInputList(['Streaming','Load days','Disch days', 'SHEX load', 'SHEX disch'], 'alignResultLightBlue', true)}
                                <Input 
                                    className='alignResultRed' 
                                    addonBefore='Total duration'
                                    name='total_duration'
                                    disabled={true}
                                    value={this.state.voyest.total_duration}/>
                            </Col>
                        </Row>

                        <p>RESULTS</p>
                        {this.renderInputList(['Gross revenue', 'Sailing bunkers', 'Loadport bunkers', 'Disport bunkers', 'Total bunker cost', 'Expenses', 'Commissions', 'Taxes', 'Exins', 'Net Revenue'], 'alignResultDarkBlue', true)}
                        <Input 
                            className='alignResultRed' 
                            addonBefore='Time charter rate' 
                            name='time_charter_rate'
                            disabled={true}
                            value={this.state.voyest.time_charter_rate}/>
                        <Input 
                            className='alignResultGreen' 
                            addonBefore='Sensitivity +/- $1'
                            name='sensitivity'
                            disabled={true}
                            value={this.state.voyest.sensitivity}/>
                        
                    </Col>
                </Row>
                <br />
                <br />
            </div>
			);
	}
}
export default withRouter(VoyageEstimate);



