import React, { Component } from 'react';
import { withRouter, Link } from 'react-router-dom';
import LoadingIndicator from '../common/LoadingIndicator';
import { 
      Table,
      Popconfirm,
      Button,
      notification,
      message
} from 'antd';
import { formatHumanDate } from '../util/Helpers';
import { deleteTCEstimateById, getAllTCEstimates } from '../util/APIUtils';

class TCEstimateList extends Component {
    constructor(props) {
        super(props);
        this.state = {
            isLoading: false,
            columns: [{
                  title: 'Name',
                  dataIndex: 'name',
                  key: 'name',
                  sorter: (a, b) => a.name.localeCompare(b.name),
                  render: (name, record) =>{
                        return (
                              <Link to={{ pathname: "/timeCharterEstimate/" + record.id, state:{tcEstimate: this.record} }}>{name}</Link>
                              );
                  },
                  width: 150,
                  fixed: 'left',
            },{
                title: 'Vessel',
                dataIndex: 'vessel',
                key: 'vessel',
            },{
                title: 'Broker',
                dataIndex: 'broker',
                key: 'broker',
            },{
                title: 'Voyage',
                dataIndex: 'voyage',
                key: 'voyage',
            },{
                title: 'Account',
                dataIndex: 'account',
                key: 'account',
            },{
                title: 'Commodity',
                dataIndex: 'commodity',
                key: 'commodity',
            },{
                key: 'delete',
                render: (record) => {
                      return (
                            <Popconfirm title="Are you sure delete this?"
                                  onConfirm={this.deleteRecord.bind(this, record.id)}>
                                        <Button type="danger">Delete</Button>
                            </Popconfirm>
                )},
                width: 100,
                fixed: 'right',
            }],
            dataSource:[]
        };
    }

    componentWillMount(){
        this.getAllTCEstimates();
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
                this.setState({
                    dataSource: response ? response : [],
                    isLoading: false
                })
                console.log(response);
            }).catch(error => {
            this.setState({
                isLoading: false
            })
        });
    }

      deleteRecord(id){

            let promise;

            promise = deleteTCEstimateById(id);

            const dataSource = this.state.dataSource.filter(i => i.id !== id)
            this.setState({dataSource})

            message.success('Deleted');

      }

    render(){
            if(this.state.isLoading) {
                  return <LoadingIndicator />
            }

        return (   
            <div>
                <Button type="primary" className="add-button">
                  <Link to={{ pathname: "/timeCharterEstimate/new" }}>
                  New Time Charter Estimation
                  </Link>
                </Button>
                <Table 
                    columns={this.state.columns}
                    dataSource={this.state.dataSource}
                    scroll={{ x: 1000 }}/>
            </div>
            );
    }
}
export default withRouter(TCEstimateList);



